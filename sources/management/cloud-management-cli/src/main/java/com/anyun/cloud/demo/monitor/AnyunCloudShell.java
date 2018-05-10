package com.anyun.cloud.demo.monitor;

import com.anyun.cloud.demo.monitor.modules.Modules;
import org.springframework.shell.Bootstrap;

import java.io.IOException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class AnyunCloudShell {
    public static void main(String[] args) throws Exception {
        Modules.init(args);
        Bootstrap.main(args);
    }
}
