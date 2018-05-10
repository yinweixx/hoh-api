package test.com.anyun.cloud.management.web;

import com.anyun.cloud.management.web.common.*;
import com.anyun.cloud.management.web.common.thymeleaf.*;
import com.anyun.cloud.management.web.server.*;
import com.anyun.cloud.management.web.thymeleaf.*;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import javax.servlet.Filter;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class WebControllTest extends BaseTest {
    public static void main(String[] args) throws Exception {
        InjectorsBuilder.getBuilder().build(
                new TestThymesModule()
        );
        InjectorsBuilder.getBuilder().getInstanceByType(WebServer.class).start();
    }

    public static class TestThymesModule extends AbstractModule {
        String path = "/Users/twitchgg/Develop/Projects/anyun-cloud-demo/" +
                "sources/management/management-web/cloud-management-web-class/target";
        private String[] args = new String[]{
                "-http_port", "8081",
                "-webapp_deploy", path
        };

        @Override
        protected void configure() {

            //Application bootstrap parameters
            bind(ApplicationOptions.class).toInstance(new WebServerOptions(args));

            //Jetty embedded web server configuration
            bind(WebAppConfigBuilder.class).to(DefaultWebAppConfigBuilder.class);
            bind(HandlerListBuilder.class).to(DefaultHandlerListBuilder.class);
            bind(WebServer.class).to(DefaultWebServer.class);

            //Thymeleaf template engine configuration
            bind(ThymeleafContext.class).to(DefaultThymeleafContext.class);
            bind(ThymesApplicationVariablesBuilder.class).to(DefaultThymesApplicationVariablesBuilder.class);

            //Template and web resource (js,image...) filters
            bind(Filter.class).annotatedWith(ThymesTemplateFilter.class).to(DefaultThymesTemplateFilter.class);
            bind(Filter.class).annotatedWith(ResourceFilter.class).to(DefaultResourceFilter.class);
            bind(Filter.class).annotatedWith(RestfullyApiResourceFilter.class).to(new DefaultRestfullyApiFilter().getClass());

            //Thymeleaf template engine drive web controllers configuration
            ThymeleafControllerPackageNames packageNames = new ThymeleafControllerPackageNames()
                    .withPackage("com.anyun.cloud.management.web.controller")
                    .withPackage("com.anyun.cloud.management.api");
            bind(ThymeleafControllerPackageNames.class).toInstance(packageNames);
            bind(ThymeleafControllerClassloaderBuilder.class).to(DefaultThymeleafControllerClassloaderBuilder.class);

            //Web resource resolver configuration
            bind(ThymeleafControllerResolver.class).to(DefaultThymeleafControllerResolver.class);
            bind(ResourceResolver.class).to(DefaultResourceResolver.class);
            bind(ResourceHandler.class).annotatedWith(Names.named("text")).to(DefaultTextResourceHandler.class);
            bind(ResourceHandler.class).annotatedWith(Names.named("image")).to(DefaultImageResourceHandler.class);
            bind(ResourceHandler.class).annotatedWith(Names.named("api")).to(DefaultRestfullyApiResourceHandler.class);
        }
    }
}
