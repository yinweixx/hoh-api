package com.anyun.common.service.exchange;

import com.anyun.cloud.service.common.context.SessionContext;
import com.anyun.cloud.service.common.exchange.ExchangeBond;

import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class OutExchangeBond implements ExchangeBond {
    private String message;
    private SessionContext sessionContext;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return null;
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return null;
    }

    @Override
    public SessionContext getSessionContext() {
        return sessionContext;
    }
}

