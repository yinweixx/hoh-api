package com.anyun.cloud.management.web.common;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public interface ResourceResolver {

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    InputStream resolve(HttpServletRequest request) throws IOException;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    byte[] resolveResourceBytes(HttpServletRequest request) throws IOException;
}
