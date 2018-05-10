package com.anyun.cloud.management.web.server;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by twitchgg on 11/07/2017.
 */
public interface ResourceHandler {

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    void process(ServletRequest request, ServletResponse response) throws IOException;

    /**
     *
     * @param request
     * @return
     */
    boolean isResource(ServletRequest request);
}
