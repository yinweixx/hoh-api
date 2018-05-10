package com.anyun.cloud.service.common;

import com.anyun.cloud.service.common.exchange.Exchange;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public interface Service<T> {

    T onExchange(Exchange exchange) throws Exception;
}
