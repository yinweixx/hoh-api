package com.anyun.cloud.service.common.exchange;

import com.anyun.cloud.service.common.context.SessionContext;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public interface Exchange {
    String HTTP_HEADER_PREFIX = "API-HTTP-HEADER.";
    String HTTP_QUERY_PREFIX = "API-HTTP-QUERY.";
    String HTTP_HEADER_SOURCE = HTTP_HEADER_PREFIX + "SOURCE";

    /**
     * @return
     */
    ExchangeBond getIn();

    SessionContext getSessionContext();
}
