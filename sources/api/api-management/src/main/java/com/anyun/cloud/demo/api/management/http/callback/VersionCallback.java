package com.anyun.cloud.demo.api.management.http.callback;

import com.anyun.cloud.demo.api.management.http.response.entity.VersionResponseEntity;
import com.anyun.common.lang.http.ApiCallback;
import com.anyun.common.lang.http.entity.BaseResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class VersionCallback implements ApiCallback {

    @Override
    public BaseResponseEntity callback(HttpServletRequest request) throws Exception {
        VersionResponseEntity entity = new VersionResponseEntity();
        return entity;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }
}
