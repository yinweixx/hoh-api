package com.anyun.common.service;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 16/06/2017
 */
public class ServiceLauncher {
    public static void main(String[] args) throws Exception {
        ServiceBoot.boot(ServiceLauncher.class, args);
    }
}
