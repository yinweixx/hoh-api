package com.anyun.cloud.demo.api.management.http;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/5
 */
public class DefaultManagerApiErrorHandler extends ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultManagerApiErrorHandler.class);

    @Override
    public void doError(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
       response.sendError(response.getStatus());
    }
}
