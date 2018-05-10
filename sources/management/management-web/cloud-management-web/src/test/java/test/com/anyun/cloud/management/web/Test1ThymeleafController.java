package test.com.anyun.cloud.management.web;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
@ThymesController(mapping = "/test1")
public class Test1ThymeleafController implements ThymeleafController {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        request.getRequestDispatcher("/test.html");
        return null;
    }
}
