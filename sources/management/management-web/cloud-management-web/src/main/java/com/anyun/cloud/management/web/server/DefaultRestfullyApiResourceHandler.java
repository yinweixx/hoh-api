package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.api.API;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafContext;
import com.anyun.cloud.management.web.thymeleaf.ThymeleafControllerClassloader;
import com.anyun.common.lang.bean.InjectorsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/07/2017
 */
public class DefaultRestfullyApiResourceHandler extends AbstractRestfullyApiResourceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestfullyApiResourceHandler.class);
    private ThymeleafControllerClassloader classloader;

    @Override
    protected void process(API api) throws IOException {
        ThymeleafContext thymeleafContext = InjectorsBuilder.getBuilder().getInstanceByType(ThymeleafContext.class);
        classloader = (ThymeleafControllerClassloader) thymeleafContext.getThymeleafControllerClassloader();
        Map<String, Class<?>> apiClasses = classloader.getJaxrsApis();
        apiClasses.forEach((key, aClass) -> {
            String rootPath = aClass.getAnnotation(Path.class).value();
            Optional<Method> methodOptional = Arrays.stream(aClass.getMethods()).filter(method -> {
                Path methodPath = method.getAnnotation(Path.class);
                if (methodPath == null)
                    return false;
                String methodPathValue = methodPath.value();
                if (methodPathValue.equals("/"))
                    methodPathValue = "";
                if (api.getPath().equals(rootPath + methodPathValue)) {
                    return true;
                }
                return false;
            }).findFirst();
            try {
                Method finalMethod = methodOptional.get();
                LOGGER.debug("method [{}]", finalMethod.getName());
                //TODO jax-rs mapping implement
            } catch (NoSuchElementException e) {
                return;
            }
        });
    }
}
