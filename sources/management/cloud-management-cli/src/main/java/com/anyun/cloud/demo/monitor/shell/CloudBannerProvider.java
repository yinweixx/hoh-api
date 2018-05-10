package com.anyun.cloud.demo.monitor.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.shell.support.util.VersionUtils;
import org.springframework.stereotype.Component;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CloudBannerProvider extends DefaultBannerProvider {
    public String getBanner() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtils.readBanner(CloudBannerProvider.class, "/banner.txt"));
        sb.append("    ").append(getVersion()).append(OsUtils.LINE_SEPARATOR);
        sb.append(OsUtils.LINE_SEPARATOR);
        return sb.toString();
    }


    public String getVersion() {
        return "v1.0.0";
    }

    public String getWelcomeMessage() {
        return "Welcome to " + getProviderName() + ". For assistance press or type \"hint\" then hit ENTER.";
    }

    public String getProviderName() {
        return "Anyun Cloud Shell";
    }
}
