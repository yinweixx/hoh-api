package com.anyun.cloud.demo.common.registry.client;

import com.anyun.common.lang.options.AbstractApplicationOptions;
import org.apache.commons.cli.Option;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class RegistryOptions extends AbstractApplicationOptions {
    private static boolean HASAGR = true;
    private static boolean HAVENTARG = false;

    public RegistryOptions(String[] args) {
        super(args);
    }

    @Override
    protected void buildOptions() {
        getOptions().addOption("types",HASAGR,"Node types");
        getOptions().addOption("etcdaddr",HASAGR,"Etcd address");
    }
}
