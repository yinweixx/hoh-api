package com.anyun.cloud.management.web.controller;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BenBen嚓 on 2017/7/13.
 */
@ThymesController(mapping = "/alerts")
public class AlertsController implements ThymeleafController {
    private static final String TEMPLATE_ALERTS = "/alerts";
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        return TEMPLATE_ALERTS;
    }
}
