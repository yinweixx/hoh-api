package com.anyun.common.service.classloader;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.cloud.service.common.Service;
import com.anyun.common.service.common.Resources;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.*;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 16/06/2017
 */
public class CloudServiceClassLoader extends URLClassLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudServiceClassLoader.class);
    private static final String FILE_PROP = "services.properties";
    private static final String PREFIX_SERVICE_PKG = "service.package.";
    private List<String> servicePackageNames = new ArrayList<>();
    private List<Class<? extends Service>> allCloudServiceClasses = new ArrayList<>();

    public CloudServiceClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public CloudServiceClassLoader(URL[] urls) {
        super(urls);
    }

    public CloudServiceClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public List<Class<? extends Service>> scan() throws Exception {
        Properties properties = new Properties();
        properties.load(Resources.getResourceAsStream(this, FILE_PROP));
        Enumeration<?> propKeys = properties.propertyNames();
        while (propKeys.hasMoreElements()) {
            String key = propKeys.nextElement().toString();
            if (!key.startsWith(PREFIX_SERVICE_PKG))
                continue;
            String serviceName = properties.getProperty(key);
            LOGGER.debug("Cloud service package name: {}", serviceName);
            servicePackageNames.add(serviceName);
        }
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.addClassLoader(this);
        scanner.matchClassesWithAnnotation(CloudService.class,
                aClass -> Arrays.stream(aClass.getInterfaces()).filter(c -> {
                    if (c.getName().equals(Service.class.getName())) {
                        LOGGER.debug("Resolve cloud service class: {}", aClass);
                        allCloudServiceClasses.add((Class<? extends Service>) aClass);
                        return true;
                    }
                    return false;
                }).findAny());
        scanner.scan();
        return allCloudServiceClasses;
    }

    public List<Class<? extends Service>> getAllCloudServiceClasses() {
        return allCloudServiceClasses;
    }
}
