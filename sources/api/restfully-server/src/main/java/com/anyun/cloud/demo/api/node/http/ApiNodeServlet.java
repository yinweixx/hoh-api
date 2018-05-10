package com.anyun.cloud.demo.api.node.http;

import com.anyun.cloud.demo.api.node.core.common.HttpMessageBuidler;
import com.anyun.cloud.demo.api.node.core.common.NodeApiComponent;
import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.anyun.common.lang.HashIdGenerator;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.http.RequestUtil;
import com.anyun.common.lang.msg.GeneralMessage;
import com.anyun.common.lang.msg.NatsClient;
import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class ApiNodeServlet extends HttpServlet {
    public static final String ENCODING_DEFAULT = "utf-8";
    public static final int ERROR_404 = 404;
    public static final int ERROR_500 = 500;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiNodeServlet.class);
    private NodeApiComponent nodeApiComponent;
    private ResourceCache resourceCache;
    private String deviceId;
    private Connection connection;

    public ApiNodeServlet() {
        nodeApiComponent = InjectorsBuilder.getBuilder().getInstanceByType(NodeApiComponent.class);
        resourceCache = InjectorsBuilder.getBuilder().getInstanceByType(ResourceCache.class);
        deviceId = ((JettyApiNodeServer) InjectorsBuilder.getBuilder()
                .getInstanceByType(ApiServer.class)).getDeviceId();
        connection = InjectorsBuilder.getBuilder().getInstanceByType(NatsClient.class).getConnection();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            resourceCache.resourceIncrement();
            response.setCharacterEncoding(ENCODING_DEFAULT);
            String pathInfo = request.getPathInfo();
            String responseContentType = getResponseContentType(request);
            LOGGER.debug("Remote IP: {}", request.getRemoteAddr());
            LOGGER.debug("Path info: [{}]  [{}]  @{}", request.getServletPath(), pathInfo, request.getMethod());
            String resourceId = HashIdGenerator.generate(pathInfo);
            LOGGER.debug("Resource [{}] ID: {}", pathInfo, resourceId);

            new Thread(() -> {
                LOGGER.debug("Resource [{}] access count increment", resourceId);
            }).start();

            ApiResourceEntity resource = nodeApiComponent.findResource(resourceId, request.getMethod());
            if (resource != null) {
                LOGGER.debug("Found resource [{}]", resourceId);
            } else {
                LOGGER.warn("Not found resource [{}]", resourceId);
                response.sendError(ERROR_404, "Not found resource [" + resourceId + "]");
                return;
            }
//            deployApi(resourceId, request.getMethod(), pathInfo, resource);
            HttpMessageBuidler messageBuidler = new HttpMessageBuidler()
                    .withDeviceId(deviceId)
                    .withHttpRequest(request)
                    .withServiceNode(resourceId);
            String message = messageBuidler.build();
            Message requestMessage = connection.request(resourceId, message.getBytes(), 5000);
            if (requestMessage == null)
                throw new Exception("Resource [" + resourceId + "] service timeout");
            Gson gson = GsonUtil.getUtil().getGson();
            GeneralMessage generalMessage = gson.fromJson(new String(requestMessage.getData()), GeneralMessage.class);
            String content = generalMessage.getBody();
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (Exception e) {
            response.sendError(ERROR_500, e.getMessage());
            return;
        } finally {
            resourceCache.resourceDecrement();
        }
    }


//    private void deployApi(String resourceId, String method, String pathInfo, ApiResourceEntity resource) throws Exception {
//        boolean mustDeploy = nodeApiComponent.mustDeploy(resourceId);
//        LOGGER.debug("Resource must deploy [{}],waiting for resource deploy", mustDeploy);
//        if (!mustDeploy)
//            return;
//        ApiDeployEntity apiDeployEntity = new ApiDeployEntity();
//        apiDeployEntity.setMethod(method);
//        apiDeployEntity.setPath(pathInfo);
//        apiDeployEntity.setResource(resource);
//        apiDeployEntity.setResourceId(resourceId);
//        nodeApiComponent.deployResource(apiDeployEntity);
//    }

    private String getResponseContentType(HttpServletRequest request) throws IOException {
        String queryString = request.getQueryString();
        Map<String, List<String>> parameters = RequestUtil.getUriQueryParameters(queryString);
        List<String> formatList = parameters.get("format");
        String format = "json";
        if (formatList != null && !formatList.isEmpty())
            format = formatList.get(0);
        else
            LOGGER.debug("Response accept content type is not set,using json (default) content type");
        LOGGER.debug("Response accept content type [{}]", format);
        return format;
    }
}
