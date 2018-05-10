package com.anyun.cloud.monitor.snmp;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 16/06/2017
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        String jarFilePath = "/Users/twitchgg/Develop/Projects" +
                "/hohot-cloud-demo/sources/example-service/example1/target/" +
                "anyun-cloud-demo-service1-1.0.0.jar";
        URL url = new File(jarFilePath).toURI().toURL();
        URL[] urls = new URL[]{
                url
        };
        ClassLoader classLoader = new URLClassLoader(urls);
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.addClassLoader(classLoader);
        ScanResult result = scanner.scan();
        for (String name : result.getNamesOfAllClasses()) {
            if (!name.contains("com.anyun.example.service.impl."))
                continue;
            Class<?> aClass = Class.forName(name, true, classLoader);

            boolean isService = false;
            for (Annotation annotation : aClass.getAnnotations()) {
                if (annotation.annotationType().getName().equals("com.anyun.common.service.annotation.CloudService")) {
                    isService = true;
                    break;
                }
            }
            if (!isService)
                continue;
            System.out.println(aClass);
        }
    }
}
