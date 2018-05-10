package com.anyun.common.service.exchange;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.service.common.exchange.ExchangeBond;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.msg.GeneralMessage;
import com.anyun.common.service.common.ServiceCache;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class DefaultBondBuilder {
    private GeneralMessage inMessage;
    private String body;

    public DefaultBondBuilder withInMessage(GeneralMessage message) {
        this.inMessage = message;
        return this;
    }

    public DefaultBondBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public ExchangeBond buid() {
        String deviceId = InjectorsBuilder.getBuilder().getInstanceByType(ServiceCache.class).getDeviceId();
        ExchangeBond exchangeBond = new OutExchangeBond();
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setTo(inMessage.getFrom());
        generalMessage.setSubject(inMessage.getSubject());
        generalMessage.setMessageId(inMessage.getMessageId());
        generalMessage.setFrom(NodeType.SERVICE_NODE.name() + "." + deviceId);
        generalMessage.setBody(body);
        exchangeBond.setMessage(GsonUtil.getUtil().toJson(generalMessage));
        return exchangeBond;
    }
}
