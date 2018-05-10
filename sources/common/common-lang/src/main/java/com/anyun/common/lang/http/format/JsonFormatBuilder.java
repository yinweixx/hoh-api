package com.anyun.common.lang.http.format;

import com.anyun.common.lang.json.GsonUtil;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class JsonFormatBuilder implements FormatBuilder {

    @Override
    public String asString(Object entity) throws Exception {
        return GsonUtil.getUtil().toJson(entity);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }
}
