package com.anyun.cloud.management.web.common;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/07/2017
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestfullyApiResourceFilter {
}
