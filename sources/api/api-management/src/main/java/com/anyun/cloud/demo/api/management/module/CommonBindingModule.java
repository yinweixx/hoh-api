package com.anyun.cloud.demo.api.management.module;

import com.anyun.cloud.demo.api.management.raml.DefaultApiRamlParser;
import com.anyun.cloud.demo.api.management.raml.RamlApiRamlParser;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class CommonBindingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonBindingModule.class);

    @Override
    protected void configure() {
        bind(RamlApiRamlParser.class).to(DefaultApiRamlParser.class);
        LOGGER.info("Bind RAML parser to: {}", DefaultApiRamlParser.class.getCanonicalName());
    }
}
