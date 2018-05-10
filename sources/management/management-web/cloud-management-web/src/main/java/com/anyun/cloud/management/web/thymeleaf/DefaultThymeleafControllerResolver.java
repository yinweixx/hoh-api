package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafControllerPackageNames;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafControllerResolver;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesController;
import com.google.inject.Inject;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class DefaultThymeleafControllerResolver implements ThymeleafControllerResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThymeleafControllerResolver.class);
    private ThymeleafControllerClassloaderBuilder thymeleafControllerClassloaderBuilder;

    private ThymeleafControllerPackageNames controllerPackageNames;
    private FastClasspathScanner scanner;
    private ThymeleafControllerClassloader thymeleafControllerClassloader;

    @Inject
    public DefaultThymeleafControllerResolver(ThymeleafControllerClassloaderBuilder thymeleafControllerClassloaderBuilder,
                                              ThymeleafControllerPackageNames controllerPackageNames) throws Exception {
        this.controllerPackageNames = controllerPackageNames;
        this.thymeleafControllerClassloaderBuilder = thymeleafControllerClassloaderBuilder;
        this.thymeleafControllerClassloader = thymeleafControllerClassloaderBuilder.build();
        scanThymeleafControllers();
        scanJaxrsAPIs();
    }

    private void scanThymeleafControllers() throws Exception {
        Map<String, ThymeleafController> controllers = new HashMap<>();
        scanner = new FastClasspathScanner();
        scanner.addClassLoader(thymeleafControllerClassloader);
        scanner.matchClassesWithAnnotation(ThymesController.class,
                aClass -> Arrays.stream(aClass.getInterfaces()).filter(c -> {
                    if (!matchPackage(aClass))
                        return false;
                    ThymesController thymesController = aClass.getAnnotation(ThymesController.class);
                    LOGGER.debug("Resolve thymes controller: {}", thymesController);
                    String controllerMapping = thymesController.mapping();
                    try {
                        ThymeleafController controller = (ThymeleafController) aClass.newInstance();
                        if (controller == null)
                            return false;
                        controllers.put(controllerMapping, controller);
                        LOGGER.debug("Add thymes controller [{}] by URI [{}]", controller, controllerMapping);
                    } catch (Exception ex) {
                        return false;
                    }
                    return true;
                }).findAny());
        scanner.scan();
        thymeleafControllerClassloader.setControllers(controllers);
    }

    private void scanJaxrsAPIs() throws Exception {
        Map<String, Class<?>> apis = new HashMap<>();
        scanner = new FastClasspathScanner();
        scanner.addClassLoader(thymeleafControllerClassloader);
        scanner.matchClassesWithAnnotation(Path.class, aClass -> {
            if (!matchPackage(aClass))
                return;
            Path apiRootPath = aClass.getAnnotation(Path.class);
            String apiRootMapping = apiRootPath.value();
            LOGGER.debug("Resolve JAX-RS api [{}] root mapping [{}]", apiRootPath, apiRootMapping);
            apis.put(apiRootMapping, aClass);
            LOGGER.debug("Add JAXRS-API class [{}] by root-path [{}]", aClass, apiRootMapping);
        });
        scanner.scan();
        thymeleafControllerClassloader.setJaxrsApis(apis);
    }

    private boolean matchPackage(Class<?> aClass) {
        String classPackageName = aClass.getPackage().getName();
        for (String packageName : controllerPackageNames.getControllerPackages()) {
            if (classPackageName.startsWith(packageName))
                return true;
        }
        return false;
    }

    @Override
    public ThymeleafController resolve(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String controllerPath = requestURI;
        if (requestURI.endsWith(".html"))
            controllerPath = requestURI.substring(0, requestURI.length() - ".html".length());
        LOGGER.debug("Resolve controller by controller path: {}", controllerPath);
        return thymeleafControllerClassloader.getControllers().get(controllerPath);
    }

    @Override
    public ClassLoader getThymeleafControllerClassloader() {
        return thymeleafControllerClassloader;
    }
}
