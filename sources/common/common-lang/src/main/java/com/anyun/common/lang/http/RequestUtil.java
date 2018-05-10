package com.anyun.common.lang.http;

import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/05/2017
 */
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * @param queryParam
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, List<String>> getUriQueryParameters(String queryParam) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        if(queryParam == null)
            return query_pairs;
        final String[] pairs = queryParam.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key))
                query_pairs.put(key, new LinkedList<>());
            final String value = idx > 0 && pair.length() >
                    idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }

    public static String getPostBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        return sb.toString();
    }

    public static ApiCallback getApiCallback(HttpServletRequest request) throws Exception {
        String contentType = request.getContentType();
        if(StringUtils.isEmpty(contentType))
            contentType = "application/json";
        Named moduleName = Names.named(ApiServer.NAMED_CALLBACK + request.getServletPath());
        Injector injector = InjectorsBuilder.getBuilder().getInjector();
        AbstractApiCallbackBindModule apiCallbackBindModule =
                injector.getInstance(Key.get(AbstractApiCallbackBindModule.class, moduleName));
        ApiCallback callback = apiCallbackBindModule.getCallbackByName(
                request.getPathInfo(), ApiCallback.HttpMethod.valueOf(request.getMethod()));
        if (request.getMethod().equals(ApiCallback.HttpMethod.POST.name())
                || request.getMethod().equals(ApiCallback.HttpMethod.PUT.name())) {
            if (!contentType.contains(callback.getAccpetContentType())) {
                LOGGER.debug("Illegal content type [{}]", contentType);
                throw new Exception("Illegal content type [" + contentType + "]");
            }
        }
        return callback;
    }
}
