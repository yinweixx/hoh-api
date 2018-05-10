package com.anyun.cloud.service.common.exchange;

import com.anyun.cloud.service.common.context.SessionContext;

import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public interface ExchangeBond {

    String getMessage();

    void setMessage(String message);

    Map<String, List<String>> getHeaders();

    Map<String, List<String>> getQuery();

    SessionContext getSessionContext();
}
