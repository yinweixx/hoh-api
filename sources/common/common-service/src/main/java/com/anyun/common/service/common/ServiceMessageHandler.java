package com.anyun.common.service.common;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.cloud.service.common.Service;
import com.anyun.cloud.service.common.context.ServiceContext;
import com.anyun.cloud.service.common.context.SessionContext;
import com.anyun.cloud.service.common.exchange.Exchange;
import com.anyun.cloud.service.common.exchange.ExchangeBond;
import com.anyun.common.lang.RandomUtils;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.msg.GeneralMessage;
import com.anyun.common.service.context.DefauleServiceContext;
import com.anyun.common.service.context.DefaultRouter;
import com.anyun.common.service.context.DefaultSessionContext;
import com.anyun.common.service.exchange.DefaultBondBuilder;
import com.anyun.common.service.exchange.DefaultExchange;
import com.anyun.common.service.exchange.InExchangeBond;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceMessageHandler implements MessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMessageHandler.class);
    private Connection connection;
    private String resourceId;

    public ServiceMessageHandler(Connection connection) {
        this.connection = connection;
    }

    public ServiceMessageHandler(String resourceId, Connection connection) {
        this.connection = connection;
        this.resourceId = resourceId;
    }

    public ServiceMessageHandler withResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    @Override
    public void onMessage(Message message) {
        try {
            new Thread(new ServiceRunnable(resourceId, connection, message)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final class ServiceRunnable implements Runnable {
        private Message message;
        private Connection connection;
        private String resourceId;

        public ServiceRunnable(String resourceId, Connection connection, Message message) {
            this.message = message;
            this.connection = connection;
            this.resourceId = resourceId;
        }

        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            String threadName = "THREAD-SERVICE-" + RandomUtils.generateByhashId(8);
            currentThread.setName(threadName);
            LOGGER.debug("THREAD: {}", currentThread.getName());
            String content = new String(message.getData());
            LOGGER.debug("Receive message: {}", content);
            Service service = InjectorsBuilder.getBuilder().
                    getInstanceByType(ServiceCache.class).getService(resourceId);
            if (service == null) {
                LOGGER.error("Not find service by resourceId [{}]", resourceId);
            }
            CloudService cloudServiceDefine = service.getClass().getAnnotation(CloudService.class);
            ServiceContext serviceContext = new DefauleServiceContext();
            SessionContext sessionContext = new DefaultSessionContext(serviceContext, new DefaultRouter(connection));
            try {
                Exchange exchange = buildInExchange(sessionContext);
                if (cloudServiceDefine.publishApi() == false) {
                    ExchangeBond inExchangeBond = exchange.getIn();
                    List<String> sourceType = inExchangeBond.getHeaders().get(Exchange.HTTP_HEADER_SOURCE);
                    if (sourceType == null || sourceType.isEmpty() || !sourceType.get(0).startsWith("service.node.")) {
                        LOGGER.debug("No publish api service [{}]", service.getClass().getName());
                        throw new Exception("No publish api service [" + cloudServiceDefine.servicePath() + "]");
                    }
                }
                Object obj = null;
                try {
                    obj = service.getClass().getMethod("onExchange", Exchange.class).invoke(service, exchange);
                } catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
                ExchangeBond outBond = buildOutExchangeBond(obj);
                LOGGER.debug("Send Messgae: {}", outBond.getMessage());
                Message sendMessage = new Message();
                sendMessage.setSubject(message.getReplyTo());
                sendMessage.setData(outBond.getMessage().getBytes());
                connection.publish(sendMessage);
            } catch (Throwable ex) {
                ex.printStackTrace();
                ServiceErrorEntity errorEntity = new ServiceErrorEntity();
                errorEntity.setMessage(ex.getMessage());
                ExchangeBond outBond = buildOutExchangeBond(errorEntity);
                Message sendErrorMessage = new Message();
                sendErrorMessage.setSubject(message.getReplyTo());
                sendErrorMessage.setData(outBond.getMessage().getBytes());
                try {
                    connection.publish(sendErrorMessage);
                    LOGGER.debug("Publish error: {}", errorEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("Send message error: {}", e.getMessage(), e);
                }
            }
        }

        private Exchange buildInExchange(SessionContext sessionContext) throws Exception {
            InExchangeBond exchangeBond = new InExchangeBond(message);
            DefaultExchange exchange = new DefaultExchange(sessionContext, exchangeBond);
            return exchange;
        }

        private ExchangeBond buildOutExchangeBond(Object obj) {
            Gson gson = new GsonBuilder().create();
            String fromJson = new String(message.getData());
            GeneralMessage generalMessage = gson.fromJson(fromJson, GeneralMessage.class);
            DefaultBondBuilder bondBuilder = new DefaultBondBuilder().withInMessage(generalMessage);
            if (obj != null) {
                bondBuilder.withBody(gson.toJson(obj));
            }
            return bondBuilder.buid();
        }
    }
}
