package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.PathMapping;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.common.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
@PathMapping(mapping = {"/*"})
public class DefaultThymesTemplateFilter extends AbstractThymesTemplateFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThymesTemplateFilter.class);

    @Override
    protected boolean process(HttpServletRequest request, HttpServletResponse response, WebContext webContext)
            throws ServletException, IOException {
        LOGGER.debug("Request URI: {}", request.getRequestURI());
        ThymeleafController controller = getThymeleafContext().resolveControllerForRequest(request);
        if (controller == null)
            response.sendError(404, "Unsupported controller by [ " + request.getRequestURI() + "]");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            String templateURI = controller.process(request, response, webContext);
            if (StringUtils.isEmpty(templateURI))
                return false;
            getThymeleafContext().templateProcess(webContext, templateURI, response.getWriter());
        } catch (Exception ex) {
            response.sendError(501, ex.getMessage());
            return false;
        }
        return true;
    }
}
