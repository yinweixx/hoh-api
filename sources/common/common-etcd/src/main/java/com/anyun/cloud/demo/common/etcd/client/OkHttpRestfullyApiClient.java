package com.anyun.cloud.demo.common.etcd.client;

import com.anyun.common.lang.StringUtils;
import com.google.inject.Inject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class OkHttpRestfullyApiClient implements HttpRestfullyApiClient {
    public static final MediaType MIME_TEXT = MediaType.parse("text/html; charset=utf-8");
    public static final MediaType MIME_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MIME_URLENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpRestfullyApiClient.class);
    private ClientConfig config;
    private OkHttpClient client;

    /**
     * @param config
     */
    @Inject
    public OkHttpRestfullyApiClient(ClientConfig config) {
        this.config = config;
        client = new OkHttpClient();
    }

    /**
     * @param pathSegments
     * @param queryParameters
     * @return
     * @throws IOException
     */
    @Override
    public String get(String pathSegments, Map<String, List<Object>> queryParameters) throws IOException {
        Request.Builder builder = getBaseBuilder(pathSegments, queryParameters);
        Request request = builder
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * @param pathSegments
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    @Override
    public String post(String pathSegments, String value, String type) throws IOException {
        RequestBody body = getRequestBody(value, type);
        return post(pathSegments, body);
    }

    /**
     * @param pathSegments
     * @param body
     * @return
     * @throws IOException
     */
    public String post(String pathSegments, RequestBody body) throws IOException {
        Request.Builder builder = getBaseBuilder(pathSegments, null);
        Request request = builder
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * @param pathSegments
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    @Override
    public String put(String pathSegments, String value, String type) throws IOException {
        RequestBody body = getRequestBody(value, type);
        return put(pathSegments, body);
    }

    /**
     * @param pathSegments
     * @param body
     * @return
     * @throws IOException
     */
    public String put(String pathSegments, RequestBody body) throws IOException {
        Request.Builder builder = getBaseBuilder(pathSegments, null);
        Request request = builder
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * @param pathSegments
     * @param queryParameters
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    @Override
    public String del(String pathSegments, Map<String, List<Object>> queryParameters,
                      String value, String type) throws IOException {
        RequestBody body = getRequestBody(value, type);
        return del(pathSegments, queryParameters, body);
    }

    /**
     * @param pathSegments
     * @param queryParameters
     * @param body
     * @return
     * @throws IOException
     */
    public String del(String pathSegments, Map<String, List<Object>> queryParameters,
                      RequestBody body) throws IOException {
        Request.Builder builder = getBaseBuilder(pathSegments, queryParameters);
        Request request = null;
        if (body == null)
            request = builder
                    .delete()
                    .build();
        else
            request = builder
                    .delete(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * @param pathSegments
     * @param queryParameters
     * @return
     */
    private HttpUrl getBaseHttpUrl(String pathSegments, Map<String, List<Object>> queryParameters) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme(config.getSchema());
        urlBuilder.port(config.getPort());
        urlBuilder.host(config.getHost());
        urlBuilder.addPathSegments(config.getBasePathSegment() + pathSegments);
        if (queryParameters != null && !queryParameters.isEmpty()) {
            for (Map.Entry<String, List<Object>> parameter : queryParameters.entrySet()) {
                String name = parameter.getKey();
                for (Object value : parameter.getValue()) {
                    urlBuilder.addQueryParameter(parameter.getKey(), value.toString());
                }
            }
        }
        HttpUrl httpUrl = urlBuilder.build();
        return httpUrl;
    }

    /**
     * @param pathSegments
     * @param queryParameters
     * @return
     */
    private Request.Builder getBaseBuilder(String pathSegments, Map<String, List<Object>> queryParameters) {
        HttpUrl httpUrl = getBaseHttpUrl(pathSegments, queryParameters);
        LOGGER.debug("HTTP URL: {}", httpUrl);
        Request.Builder builder = new Request.Builder().url(httpUrl);
        return builder;
    }

    /**
     * @param value
     * @param type
     * @return
     */
    private RequestBody getRequestBody(String value, String type) {
        if (StringUtils.isEmpty(value))
            return null;
        MediaType mediaType = null;
        if (StringUtils.isEmpty(type))
            mediaType = MIME_URLENCODED;
        return RequestBody.create(mediaType, value);
    }

    public ClientConfig getConfig() {
        return config;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
