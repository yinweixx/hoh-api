package com.anyun.common.service.exchange;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.service.common.context.SessionContext;
import com.anyun.cloud.service.common.exchange.Exchange;
import com.anyun.cloud.service.common.exchange.ExchangeBond;
import com.anyun.common.lang.msg.GeneralMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nats.client.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class InExchangeBond implements ExchangeBond {
    private static final Logger LOGGER = LoggerFactory.getLogger(InExchangeBond.class);
    private GeneralMessage message;
    private Map<String, List<String>> query;
    private SessionContext sessionContext;

    public InExchangeBond(Message inMessage) throws Exception {
        Gson gson = new GsonBuilder().create();
        String data = new String(inMessage.getData());
        GeneralMessage generalMessage = gson.fromJson(data, GeneralMessage.class);
        LOGGER.debug("Message: ", generalMessage);
        this.message = generalMessage;
    }

    @Override
    public String getMessage() {
        return message.getBody();
    }

    @Override
    public void setMessage(String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        Map<String, List<String>> allHeaders = new HashMap<>();
        query = new HashMap<>();
        for (Map.Entry<String, String> head : message.getHeaders().entrySet()) {
            String headName = head.getKey();
            String headValue = head.getValue();
            if (headValue.startsWith("[") && headValue.endsWith("]")) {
                LOGGER.debug("Build head value list [{}]", headValue);
                List<String> values = GsonUtil.getUtil().getGson().fromJson(headValue, List.class);
                if (headName.startsWith(Exchange.HTTP_QUERY_PREFIX))
                    query.put(headName.substring(Exchange.HTTP_QUERY_PREFIX.length()), values);
                allHeaders.put(headName, values);
            } else {
                if (headName.startsWith(Exchange.HTTP_QUERY_PREFIX))
                    query.put(headName.substring(Exchange.HTTP_QUERY_PREFIX.length()), Arrays.asList(headValue));
                allHeaders.put(headName, Arrays.asList(headValue));
            }

        }
        return allHeaders;
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return query;
    }

    @Override
    public SessionContext getSessionContext() {
        return null;
    }
}
