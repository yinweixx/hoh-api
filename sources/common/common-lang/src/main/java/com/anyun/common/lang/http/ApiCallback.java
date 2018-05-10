package com.anyun.common.lang.http;

import com.anyun.common.lang.http.entity.BaseResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public interface ApiCallback<T extends BaseResponseEntity> {

    /**
     * @param request
     * @return
     * @throws Exception
     */
    BaseResponseEntity callback(HttpServletRequest request) throws Exception;

    /**
     * @return
     */
    default HttpMethod getMethod() {
        return HttpMethod.ALL;
    }

    default String getAccpetContentType() {
        return "application/json";
    }

    /**
     *
     */
    enum HttpMethod {
        GET(1),
        POST(2),
        DELETE(3),
        PUT(4),
        ALL(0);

        private int value;

        HttpMethod(int value) {
            this.value = value;
        }

        int value() {
            return value;
        }
    }
}
