package test.com.anyun.cloud.management.web;

import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
//注解路径为/test
@ThymesController(mapping = "/test")
public class TestThymeleafController implements ThymeleafController {
    private static final String TEMPLATE_TEST = "/test";

    /**
     * public final void setVariable(String name, Object value) {
     this.variables.put(name, value);
     }
     */
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response, WebContext context) throws Exception {
        context.setVariable("entity", new TestEntity("张三", new Date()));
        return TEMPLATE_TEST;
    }

    public class TestEntity {
        private String name;
        private Date date;

        public TestEntity(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

            public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
