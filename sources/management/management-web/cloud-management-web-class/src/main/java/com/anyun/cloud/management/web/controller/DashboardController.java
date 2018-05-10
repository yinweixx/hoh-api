package com.anyun.cloud.management.web.controller;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
@ThymesController(mapping = "/dashboard")
public class DashboardController implements ThymeleafController {
    private static final String TEMPLATE_TEST = "/dashboard";

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        //TODO Dashboard page dynamic data filling
        return TEMPLATE_TEST;
    }
}
