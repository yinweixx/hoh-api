package com.anyun.cloud.management.web.common.thymeleaf;

import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public interface ThymeleafController {
    String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception;
}
