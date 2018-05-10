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
@ThymesController(mapping = "/index")
public class IndexController implements ThymeleafController {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        System.out.println("Classloader for controller: " + this.getClass().getClassLoader());
        //TODO Index page dynamic data filling
//        request.getRequestDispatcher("/dashboard.html").forward(request, response);
        response.sendRedirect("/dashboard.html");
        return null;
    }
}
