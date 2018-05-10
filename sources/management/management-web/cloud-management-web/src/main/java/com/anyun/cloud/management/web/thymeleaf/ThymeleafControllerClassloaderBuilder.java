package com.anyun.cloud.management.web.thymeleaf;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/06/2017
 */
public interface ThymeleafControllerClassloaderBuilder {

    /**
     * <pre>
     * build thymeleaf controller classloader
     * include thymeleaf controllers and jax-rs apis
     * </pre>
     *
     * @return
     * @throws Exception
     */
    ThymeleafControllerClassloader build() throws Exception;
}
