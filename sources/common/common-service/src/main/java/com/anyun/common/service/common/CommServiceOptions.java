package com.anyun.common.service.common;

import com.anyun.cloud.demo.common.registry.client.RegistryOptions;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class CommServiceOptions extends RegistryOptions {

    public CommServiceOptions(String[] args) {
        super(args);
    }

    @Override
    protected void buildOptions() {
        super.buildOptions();
        getOptions().addOption("service_dir", true, "Service deploy directory");
    }
}
