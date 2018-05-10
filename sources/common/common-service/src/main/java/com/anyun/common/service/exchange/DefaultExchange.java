package com.anyun.common.service.exchange;

import com.anyun.cloud.service.common.context.SessionContext;
import com.anyun.cloud.service.common.exchange.Exchange;
import com.anyun.cloud.service.common.exchange.ExchangeBond;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class DefaultExchange implements Exchange {
    private ExchangeBond inBond;
    private SessionContext sessionContext;

    public DefaultExchange(SessionContext sessionContext, ExchangeBond inBond) {
        this.inBond = inBond;
        this.sessionContext = sessionContext;
    }

    @Override
    public ExchangeBond getIn() {
        return inBond;
    }

    @Override
    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
