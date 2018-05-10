package com.anyun.cloud.management.web.controller;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BenBen嚓 on 2017/7/21.
 */
@ThymesController(mapping = "/createCharts")
public class CreateChartsController implements ThymeleafController {
    private static final String TEMPLATE_CREATECHARTS = "/createCharts";
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        return TEMPLATE_CREATECHARTS;
    }
}
