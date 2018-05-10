package com.anyun.common.service.common;

import com.anyun.cloud.service.common.Service;
import com.anyun.cloud.service.common.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarInputStream;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class PackageScanClassResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageScanClassResolver.class);
    private ClassLoader classLoader;
    private Class<?> baseClass;

    public PackageScanClassResolver withClassLoad(ClassLoader classLoad) {
        this.classLoader = classLoad;
        return this;
    }

    public PackageScanClassResolver withBaseClass(Class<?> baseClass) {
        this.baseClass = baseClass;
        return this;
    }

    private List<Class<?>> scan(String path) {
        List<Class<?>> classes = new ArrayList<>();
        if (classLoader == null) {
            if (baseClass == null)
                classLoader = Thread.currentThread().getContextClassLoader();
            else
                classLoader = baseClass.getClassLoader();
        }
        try {
            URL url = Resources.getResourceURL(classLoader, path);
            LOGGER.debug("Resolve file path: {}", url.getFile());
            File files[] = new File(url.getFile()).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".class"))
                        return true;
                    return false;
                }
            });
            JarInputStream jarInputStream = new JarInputStream(null);
            LOGGER.debug("Resolve file names: {}", files);
            for (File file : files) {
                String classNameTemp = path.replace("/", ".") + "." + file.getName();
                String className = classNameTemp.substring(0, classNameTemp.length() - ".class".length());
                Class<?> loadClass = Class.forName(className);
                LOGGER.debug("Resolve class: {}", loadClass.getName());
                classes.add(loadClass);
            }
            return classes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return classes;
        }
    }

    public List<Class<?>> scanByPackageName(String packageName) {
        return scan(packageName.replace(".", "/"));
    }

    public List<Class<? extends Service>> scanCloudServiceClassByPackageName(String packageName) {
        List<Class<? extends Service>> cloudServiceClasses = new ArrayList<>();
        for (Class<?> loadClass : scanByPackageName(packageName)) {
            if (loadClass.getAnnotation(CloudService.class) != null) {
                Arrays.stream(loadClass.getInterfaces()).filter(c -> {
                    if (c.getName().equals(Service.class.getName())) {
                        LOGGER.debug("Resolve cloud service class: {}", loadClass);
                        cloudServiceClasses.add((Class<? extends Service>) loadClass);
                        return true;
                    }
                    return false;
                }).findAny();
            }
        }
        return cloudServiceClasses;
    }
}
