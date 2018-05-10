package com.anyun.cloud.demo.api.management.module;

import com.anyun.cloud.demo.api.management.http.callback.ApiDeployCallback;
import com.anyun.cloud.demo.api.management.http.callback.VersionCallback;
import com.anyun.common.lang.http.AbstractApiCallbackBindModule;
import com.google.inject.Singleton;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
@Singleton
public class ManagementApiCallbackBindModule extends AbstractApiCallbackBindModule {
    public static String API_VERSION = "/version";
    public static String API_DEPLOY = "/deploy";

    @Override
    public void configure() {
        addApi(API_VERSION, new VersionCallback());
        addApi(API_DEPLOY, new ApiDeployCallback());
    }
}
