package com.anyun.common.lang.http.format;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public interface FormatBuilder {
    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    String asString(Object entity) throws Exception;

    /**
     *
     * @return
     */
    String getContentType();
}
