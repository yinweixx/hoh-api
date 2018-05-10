package com.anyun.cloud.management.web.controller;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by BenBen嚓 on 2017/8/14.
 */
@ThymesController(mapping = "/statistic")
public class StatisticController implements ThymeleafController {
    private static final String TEMPLATE_STATISTIC = "/statistic";

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        return TEMPLATE_STATISTIC;

    }
}