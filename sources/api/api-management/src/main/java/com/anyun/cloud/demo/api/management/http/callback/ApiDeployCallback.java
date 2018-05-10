package com.anyun.cloud.demo.api.management.http.callback;

import com.anyun.cloud.demo.api.management.core.service.ApiManagementService;
import com.anyun.common.lang.*;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.http.ApiCallback;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.http.entity.BaseResponseEntity;
import com.anyun.common.lang.http.entity.DefaultResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class ApiDeployCallback implements ApiCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDeployCallback.class);
    private static final String RES_CLASSPATH = "classpath:";
    private static final String RES_FILE = "file:";
    private ApiManagementService apiManagementService;

    public ApiDeployCallback() {
        apiManagementService = InjectorsBuilder.getBuilder().getInstanceByType(ApiManagementService.class);
    }

    @Override
    public BaseResponseEntity callback(HttpServletRequest request) throws Exception {
        int contentSize = request.getContentLength();
        LOGGER.debug("Upload content size: {} bytes", contentSize);
        String uploadDir = getServerUploadDirectory();
        FileUtil.mkdir(uploadDir, false);
        String expression = "yyyyMMdd-HHmmss";
        String filePrefix = "/" + StringUtils.formatDate(new Date(), expression) + "-";
        String randomFileName = RandomUtils.generateByhashId(4);
        String fileName = uploadDir + filePrefix + randomFileName;
        LOGGER.debug("Upload file [{}]", fileName);
        FileUtil.write(fileName, request.getInputStream(), false);
        String extractDir = uploadDir + "/../extract/" + filePrefix + randomFileName;
        LOGGER.debug("Unzip directory: {}", extractDir);
        FileUtil.mkdir(extractDir, false);
        try {
            ZipUtils.extractZipData(fileName, new File(extractDir));
        } catch (Exception ex) {
            DefaultResponseEntity response = new DefaultResponseEntity();
            response.setCode(500);
            if (ex instanceof FileNotFoundException) {
                if (ex.getMessage().contains(".rels"))
                    response.setMessage("Upload file is not zip format");
            } else
                response.setMessage(ex.getMessage());
            clearDeployFiles(fileName, extractDir);
            return response;
        }
        try {
            apiManagementService.deploy(extractDir);
        } catch (Exception ex) {
            DefaultResponseEntity response = new DefaultResponseEntity();
            response.setCode(500);
            response.setMessage(ex.getMessage());
            return response;
        } finally {
            clearDeployFiles(fileName, extractDir);
        }
        return null;
    }

    private void clearDeployFiles(String upload, String extract) {
        FileUtil.rm(upload, true);
        FileUtil.rm(extract, true);
    }

    private String getServerUploadDirectory() throws Exception {
        String uploadDirConfig = InjectorsBuilder.getBuilder().getInstanceByType(ApiServer.class).getConfig().getUploadDir();
        LOGGER.debug("upload config dir [{}]: ", uploadDirConfig);
        String uploadDir = null;
        if (uploadDirConfig.startsWith(RES_CLASSPATH)) {
            String tmp = uploadDirConfig.substring(RES_CLASSPATH.length());
            if (!tmp.startsWith("./") && !tmp.startsWith("/"))
                tmp = "./" + tmp;
            uploadDir = Resources.getResourceURL("/").getFile() + tmp;
            LOGGER.debug("Server upload classpath [{}]", tmp);
        } else if (uploadDirConfig.startsWith(RES_FILE)) {
            uploadDir = uploadDirConfig.substring(RES_FILE.length());
        } else {
            throw new Exception("Unsupported upload file path type ["
                    + uploadDirConfig.substring(0, uploadDir.indexOf(':')) + "]");
        }
        LOGGER.debug("upload dir [{}]: ", uploadDir);
        return uploadDir;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PUT;
    }

    @Override
    public String getAccpetContentType() {
        return "application/zip";
    }
}
