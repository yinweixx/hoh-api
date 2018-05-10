package com.anyun.common.lang.http.format;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class ResponseFormatBuilder {
    private static final String JSON = "json";
    private static Map<String, FormatBuilder> build;

    static {
        build = new HashMap<>();
        build.put(JSON, new JsonFormatBuilder());
    }

    private String type = JSON;

    public ResponseFormatBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public FormatBuilder build() {
        return build.get(type.toLowerCase());
    }
}
