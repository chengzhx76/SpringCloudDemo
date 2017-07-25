package com.chengzhx76.github.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/25
 */
public class AccessFilter extends ZuulFilter {

    private final Logger _log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        _log.info("send {} request to {}", request.getMethod(), request.getRequestURL());

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            _log.warn("access token is empty");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            return null;
        }
        _log.info("access token ok {}", accessToken);
        return null;
    }
}
