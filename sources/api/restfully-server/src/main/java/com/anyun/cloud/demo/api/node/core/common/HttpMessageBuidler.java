package com.anyun.cloud.demo.api.node.core.common;

import com.anyun.common.lang.FileUtil;
import com.anyun.common.lang.http.RequestUtil;
import com.anyun.common.lang.msg.GeneralMessage;
import com.anyun.common.lang.msg.MessageBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class HttpMessageBuidler implements MessageBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMessageBuidler.class);
    private static final List<String> SUPPORTED_CONTENT_TYPES = new ArrayList<>();
    private static final String REQUEST_ENCODING = "utf-8";
    private static final String HTTP_HEADER_PREFIX = "API-HTTP-HEADER.";
    private static final String HTTP_QUERY_PREFIX = "API-HTTP-QUERY.";
    private static final String SUBJECT = "RESOURCE-";

    static {
        SUPPORTED_CONTENT_TYPES.addAll(Arrays.asList(
                "application/json",
                "application/xml",
                "text/html",
                "text/plain",
                "text/xml"
        ));
    }

    private HttpServletRequest request;
    private String deviceId;
    private String serviceNode;

    public HttpMessageBuidler withHttpRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HttpMessageBuidler withDeviceId(String deviceId) {
        this.deviceId = "API_REST_NODE." + deviceId;
        return this;
    }

    public HttpMessageBuidler withServiceNode(String serviceNode) {
        this.serviceNode = serviceNode;
        return this;
    }


    @Override
    public String build() {
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setBody(getRequestBody());
        generalMessage.setFrom(deviceId);
        generalMessage.setHeaders(getHeaders());
        generalMessage.setMessageId(generateMessageId());
        generalMessage.setSubject(SUBJECT + serviceNode);
        generalMessage.setTo("SERVICE-NODE {RANDOM}");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(generalMessage);
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        headers.put(HTTP_HEADER_PREFIX + "REMOTE", request.getRemoteHost());
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(HTTP_HEADER_PREFIX + headerName, request.getHeader(headerName));
        }
        try {
            Map<String, List<String>> map = RequestUtil.getUriQueryParameters(request.getQueryString());
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            for (Map.Entry<String, List<String>> p : map.entrySet()) {
                headers.put(HTTP_QUERY_PREFIX + p.getKey(), gson.toJson(p.getValue()));
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Query string parse error [{}]", e.getMessage());
        }
        return headers;
    }

    private String generateMessageId() {
        String salt = UUID.randomUUID().toString();
        Hashids hashids = new Hashids(salt, 16);
        return hashids.encode(System.nanoTime());
    }

    private String getRequestBody() {
        if (request.getMethod().equals("GET")
                || request.getMethod().equals("HEAD")
                || request.getMethod().equals("DELETE")) {
            LOGGER.warn("Unsupported body parse with method [{}]", request.getMethod());
            return "";
        }
        if (!isSupportedContentType()) {
            LOGGER.warn("Unsupported request content type [{}]", request.getContentType());
            return "";
        }
        try {
            int contentSize = request.getContentLength();
            LOGGER.debug("Request content size: {} bytes", contentSize);
            if (contentSize == 0)
                return "";
            String body = FileUtil.cat(request.getInputStream(), REQUEST_ENCODING);
            LOGGER.debug("Request body [\n{}\n]", body);
            return body;
        } catch (Exception ex) {
            LOGGER.warn("Request content read error [{}]", ex.getMessage(), ex);
            return "";
        }
    }

    private boolean isSupportedContentType() {
        for (String type : SUPPORTED_CONTENT_TYPES) {
            if (type.equals(request.getContentType()))
                return true;
        }
        return false;
    }
}
