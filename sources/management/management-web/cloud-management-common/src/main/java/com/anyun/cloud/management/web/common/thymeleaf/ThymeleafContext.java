package com.anyun.cloud.management.web.common.thymeleaf;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.ResourceResolver;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public interface ThymeleafContext {
    String DIR_TEMPLATE = "templates";

    /**
     * @param request
     * @return
     */
    ThymeleafController resolveControllerForRequest(HttpServletRequest request);

    /**
     * @return
     */
    ResourceResolver getResourceResolver();

    /**
     * @param context
     * @param templateURI
     * @param writer
     * @throws Exception
     */
    void templateProcess(WebContext context, String templateURI, Writer writer) throws Exception;

    /**
     *
     * @return
     */
    ClassLoader getThymeleafControllerClassloader();
}
