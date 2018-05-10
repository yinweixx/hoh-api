package com.anyun.cloud.demo.common.registry.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class GsonUtil {
    private static GsonUtil util;
    private Gson gson;

    private GsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    public static GsonUtil getUtil() {
        synchronized (GsonUtil.class) {
            if(util == null)
                util = new GsonUtil();
        }
        return util;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
