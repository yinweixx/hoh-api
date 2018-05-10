package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafContext;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesApplicationVariablesBuilder;
import com.anyun.common.lang.bean.InjectorsBuilder;
import org.thymeleaf.context.WebContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public abstract class AbstractThymesTemplateFilter implements Filter {
    private static final String TEMPLATE_SUFFIX = ".html";
    private static final String VARIABLES_KEY = "web_app_variables";
    private ServletContext servletContext;
    private ThymeleafContext thymeleafContext;

    /**
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    protected abstract boolean process(HttpServletRequest request, HttpServletResponse response, WebContext ctx)
            throws ServletException, IOException;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!((HttpServletRequest) request).getRequestURI().toLowerCase().endsWith(TEMPLATE_SUFFIX)) {
            chain.doFilter(request, response);
            return;
        }
        WebContext ctx = new WebContext(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                servletContext, request.getLocale());
        ctx.setVariables(getApplicationVariables((HttpServletRequest) request));
        if (!process((HttpServletRequest) request, (HttpServletResponse) response, ctx))
            chain.doFilter(request, response);
    }

    private Map<String, Object> getApplicationVariables(HttpServletRequest request) {
        Map<String, Object> variablesMap = new HashMap<>();
        ThymesApplicationVariablesBuilder variablesBuilder = InjectorsBuilder.getBuilder()
                .getInstanceByType(ThymesApplicationVariablesBuilder.class);
        variablesMap.put(VARIABLES_KEY, variablesBuilder.build(servletContext, request));
        return variablesMap;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        thymeleafContext = InjectorsBuilder.getBuilder().getInstanceByType(ThymeleafContext.class);
    }

    @Override
    public void destroy() {
        servletContext = null;
        thymeleafContext = null;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public ThymeleafContext getThymeleafContext() {
        return thymeleafContext;
    }
}
