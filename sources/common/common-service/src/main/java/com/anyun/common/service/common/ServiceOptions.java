package com.anyun.common.service.common;

import com.anyun.common.lang.options.AbstractApplicationOptions;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceOptions extends AbstractApplicationOptions {

    public ServiceOptions(String[] args) {
        super(args);
    }

    @Override
    protected void buildOptions() {
        getOptions().addOption("etcdaddr", true, "Etcd address");
    }
}
