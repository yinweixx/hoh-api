package com.anyun.common.lang.http;

import com.anyun.common.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public abstract class AbstractApiCallbackBindModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApiCallbackBindModule.class);

    private Map<String, ApiCallback> apiCallbackMap;

    public AbstractApiCallbackBindModule() {
        apiCallbackMap = new HashMap<>();
        configure();
    }

    public abstract void configure();

    protected final void addApi(String path, ApiCallback callback) {
        apiCallbackMap.put(path, callback);
        LOGGER.debug("Bind api path [{}] to: {}", path, callback.getClass());
    }

    public final ApiCallback getCallbackByName(String name, ApiCallback.HttpMethod method) {
        if (StringUtils.isEmpty(name))
            return null;
        name = name.replace("//", "/");
        ApiCallback apiCallback = apiCallbackMap.get(name);
        if (apiCallback == null) {
            LOGGER.debug("Api callback [{}] not found", name);
            return null;
        }
        if (apiCallback.getMethod().equals(ApiCallback.HttpMethod.ALL))
            return apiCallback;
        if (apiCallback.getMethod().equals(method))
            return apiCallback;
        LOGGER.debug("Api callback [{}] method [{}] is not allow", name, method.name());
        return null;
    }
}
