package com.anyun.common.service.git;

import com.anyun.common.lang.FileUtil;
import com.anyun.common.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class DefaultGitService implements GitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGitService.class);
    private static final String TAG_PREFIX = "refs/tags/";
    private static final String ENV_GIT_REMOTE = "SERVICE_GIT_URL";
    private static final String ENV_SERVICE_DEPLOY_DIR = "SERVICE_DEPLOY_DIR";

    @Override
    public List<String> getAllRefTags() throws Exception {
        List<String> tagNames = new ArrayList<>();
        Collection<Ref> refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(getRemoteUrl())
                .call();
        for (Ref ref : refs) {
            if (ref.getName().startsWith(TAG_PREFIX))
                tagNames.add(ref.getName().substring(TAG_PREFIX.length()));
        }
        return tagNames;
    }

    @Override
    public void pullServiceWithTag(String tag) throws Exception {
        String remote = getRemoteUrl();
        File localPath = new File(getServiceDeployDir());
        FileUtil.rm(localPath.getPath(), true);
        LOGGER.debug("Cloning from {} to {}", remote, localPath);
        Git.cloneRepository()
                .setURI(remote)
                .setDirectory(localPath)
                .setBranch(tag)
                .call();
    }

    @Override
    public void autoPullService() throws Exception {
        List<String> tags = getAllRefTags();
        String deployTag = null;
        String format = "yyyyMMddHHmm";
        LocalDateTime lastTime = null;
        for (String tag : tags) {

            if (!tag.startsWith("v"))
                continue;
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
                LocalDateTime parseTime = LocalDateTime.parse(tag.substring(1), dateTimeFormatter);
                if (parseTime == null)
                    continue;
                LOGGER.debug("tag: {}", tag);
                if (lastTime == null) {
                    lastTime = parseTime;
                    deployTag = tag;
                } else if (parseTime.isAfter(lastTime)) {
                    lastTime = parseTime;
                    deployTag = tag;
                }

            } catch (Exception ex) {
                continue;
            }
        }
        if (StringUtils.isEmpty(deployTag))
            deployTag = "master";
        LOGGER.debug("Fetch service last version tag: {}", deployTag);
        pullServiceWithTag(deployTag);
    }

    private String getServiceDeployDir() throws Exception {
//        String dir = System.getenv(ENV_SERVICE_DEPLOY_DIR);
        String dir = System.getenv(ENV_SERVICE_DEPLOY_DIR);
        if (StringUtils.isEmpty(dir))
            dir = "/opt/services";
        return dir;
    }

    private String getRemoteUrl() throws Exception {
//        String remote = System.getenv(ENV_GIT_REMOTE);
        String remote = System.getenv(ENV_GIT_REMOTE);
        if (StringUtils.isEmpty(remote))
            throw new Exception("Git remote is null");
        return remote;
    }
}
