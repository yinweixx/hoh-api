package com.anyun.cloud.demo.monitor.shell;

import com.anyun.common.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CloudPromptProvider extends DefaultPromptProvider {
    private String user = "guest";

    @Override
    public String getPrompt() {
        return StringUtils.formatDate(new Date(), "HH:mm:ss") + " >> ";
    }

    @Override
    public String getProviderName() {
        return "Cloud prompt provider";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
