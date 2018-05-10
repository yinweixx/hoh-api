package com.anyun.common.lang.http;

import com.anyun.common.lang.http.entity.BaseResponseEntity;
import com.anyun.common.lang.http.entity.DefaultResponseEntity;
import com.anyun.common.lang.http.entity.ResponseRootNode;
import com.anyun.common.lang.http.format.FormatBuilder;
import com.anyun.common.lang.http.format.ResponseFormatBuilder;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
@Singleton
public class DefaultApiServlet extends HttpServlet {
    public static final int ERROR_404 = 404;
    public static final int ERROR_500 = 500;
    public static final String ENCODING_DEFAULT = "utf-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApiServlet.class);

    public DefaultApiServlet() {
        System.out.println("Initial api servlet");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseRootNode rootNode = new ResponseRootNode();
        long current = System.currentTimeMillis();
        response.setCharacterEncoding(ENCODING_DEFAULT);
        String pathInfo = request.getPathInfo();
        String responseContentType = getResponseContentType(request);
        LOGGER.debug("Remote IP: {}", request.getRemoteAddr());
        LOGGER.debug("Path info: {}{}  @{}", request.getServletPath(), pathInfo, request.getMethod());
        try {
            ApiCallback callback = RequestUtil.getApiCallback(request);
            if (callback == null) {
                LOGGER.debug("Not found api callback by path info [{}]", pathInfo);
                String errorMessage = "Not found api callback by path info [" + pathInfo + "]";
                response.sendError(ERROR_404, errorMessage);
                return;
            }

            BaseResponseEntity entity = callback.callback(request);
            if (entity == null)
                entity = new DefaultResponseEntity();
            rootNode.setResult(entity);
            rootNode.setAction(pathInfo);
            FormatBuilder formatBuilder = new ResponseFormatBuilder().withType(responseContentType).build();
            LOGGER.debug("Matched format msg [{}]", formatBuilder);
            if (formatBuilder == null)
                throw new Exception("Unsupported format type [" + responseContentType + "]");
            rootNode.setSystemDate(new Date(System.currentTimeMillis()));
            rootNode.setExecMillisecond(System.currentTimeMillis() - current);
            String responseString = formatBuilder.asString(rootNode);
            LOGGER.debug("Response string [\n{}\n]", responseString);
            response.setContentType(formatBuilder.getContentType());
            PrintWriter writer = response.getWriter();
            writer.write(responseString);
            writer.flush();
        } catch (Exception e) {
            LOGGER.debug("Api callback [{}] error: {}", pathInfo, e.getMessage(), e);
            String errorMessage = "Api callback [" + pathInfo + "] error: " + e.getMessage();
            response.sendError(ERROR_500, errorMessage);
            return;
        }
    }

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
