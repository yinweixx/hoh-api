package com.anyun.cloud.demo.api.management.raml;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiEntity;
import org.raml.v2.api.model.v10.api.Api;

import java.io.File;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
public interface RamlApiRamlParser {
    String ENCODING = "utf-8";
    /**
     * @param raml
     * @return
     */
    DefaultApiRamlParser withRamlString(String raml);

    /**
     * @param file
     * @return
     */
    DefaultApiRamlParser withRamlFile(File file);

    /**
     * @param encoding
     * @return
     */
    DefaultApiRamlParser withEncoding(String encoding);

    /**
     * @return
     * @throws Exception
     */
    Api buildV10Api() throws Exception;

    /**
     * @return
     * @throws Exception
     */
    ApiEntity buildApi() throws Exception;
}
