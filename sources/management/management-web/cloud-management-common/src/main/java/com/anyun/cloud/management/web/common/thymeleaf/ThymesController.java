package com.anyun.cloud.management.web.common.thymeleaf;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
@BindingAnnotation
@Target({TYPE, FIELD, PARAMETER, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThymesController {
    String mapping();
}
