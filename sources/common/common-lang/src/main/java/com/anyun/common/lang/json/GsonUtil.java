package com.anyun.common.lang.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class GsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtil.class);
    private static GsonUtil util;
    private Gson gson;

    private GsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = builder.create();
    }

    public static GsonUtil getUtil() {
        synchronized (GsonUtil.class) {
            if (util == null)
                util = new GsonUtil();
        }
        return util;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Gson getGson() {
        return gson;
    }

}
