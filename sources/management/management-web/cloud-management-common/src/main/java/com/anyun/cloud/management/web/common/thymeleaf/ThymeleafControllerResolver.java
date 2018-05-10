package com.anyun.cloud.management.web.common.thymeleaf;

import javax.servlet.http.HttpServletRequest;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public interface ThymeleafControllerResolver {

    /**
     * resolve thymeleaf template controller
     * @param request
     * @return
     */
    ThymeleafController resolve(HttpServletRequest request);

    /**
     * get thymeleaf template controller classloader
     * @return
     */
    ClassLoader getThymeleafControllerClassloader();
}
