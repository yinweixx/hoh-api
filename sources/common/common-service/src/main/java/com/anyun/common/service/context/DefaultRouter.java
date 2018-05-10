package com.anyun.common.service.context;

import com.anyun.cloud.service.common.Router;
import com.anyun.common.lang.HashIdGenerator;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.msg.GeneralMessage;
import com.anyun.common.service.common.ServiceCache;
import com.anyun.common.service.common.ServiceErrorEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nats.client.Connection;
import io.nats.client.Message;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class DefaultRouter implements Router {
    private static final String HTTP_HEADER_PREFIX = "API-HTTP-HEADER.";
    private static final String HTTP_QUERY_PREFIX = "API-HTTP-QUERY.";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRouter.class);
    private static final String SUBJECT = "RESOURCE-";
    private Connection connection;

    public DefaultRouter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T route(String resourceId, Map<String, String> headers, String requestBody, Class<T> responseType) throws Exception {
        if (StringUtils.isEmpty(requestBody))
            requestBody = "";
        String deviceId = InjectorsBuilder.getBuilder().getInstanceByType(ServiceCache.class).getDeviceId();
        String hashId = HashIdGenerator.generate(resourceId);
        LOGGER.debug("Resource [{}] router", hashId);
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setBody(requestBody);
        generalMessage.setFrom(deviceId);
        generalMessage.setHeaders(getHeaders(headers, deviceId));
        generalMessage.setMessageId(generateMessageId());
        generalMessage.setSubject(SUBJECT + hashId);
        generalMessage.setTo("SERVICE-NODE {RANDOM}");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String messageContent = gson.toJson(generalMessage);
        Message message = connection.request(hashId, messageContent.getBytes(), 5000);
        if (message == null)
            throw new Exception("Resource [" + resourceId + "] response timeout");
        String responseData = new String(message.getData());
        LOGGER.debug("Resource [{}]  route response: {}", resourceId, responseData);
        GeneralMessage responseEntity = gson.fromJson(responseData, GeneralMessage.class);
        try {
            return (T) gson.fromJson(responseEntity.getBody(), responseType);
        } catch (Exception ex) {
            ServiceErrorEntity exception = gson.fromJson(responseEntity.getBody(), ServiceErrorEntity.class);
            LOGGER.debug("Route error: {}", exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }

    private String generateMessageId() {
        String salt = UUID.randomUUID().toString();
        Hashids hashids = new Hashids(salt, 16);
        return hashids.encode(System.nanoTime());
    }

    private Map<String, String> getHeaders(Map<String, String> headers, String deviceId) {
        Map<String, String> allHeaders = new HashMap<>();
        allHeaders.put(HTTP_HEADER_PREFIX + "SOURCE", "service.node." + deviceId);
        if (headers == null)
            return allHeaders;
        for (Map.Entry<String, String> head : headers.entrySet()) {
            allHeaders.put(HTTP_QUERY_PREFIX + head.getKey(), head.getValue());
        }
        return allHeaders;
    }
}
