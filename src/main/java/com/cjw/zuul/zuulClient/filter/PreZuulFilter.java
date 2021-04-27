package com.cjw.zuul.zuulClient.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class PreZuulFilter extends ZuulFilter {
    /* 라우팅 이전에 실행될 필터
    * 클라이언트의 IP 체크, 허용된 대역이 아닐경우 403 리턴 */

    @Override
    public String filterType() {
        /* filter의 타입 정의 (pre, route, post, error) */
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        /* filter 의 실행 순서 정의 */
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        /* filter의 실행 여부 */
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        /* filter 가 실행할 로직 */
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        if (!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_FORBIDDEN);
        }

        return null;
    };
}
