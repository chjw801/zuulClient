package com.cjw.zuul.zuulClient.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

public class PostZuulFilter extends ZuulFilter {
    /* 라우팅 이후에 실행될 필터
     * UUID 정보를 HTTP 헤더에 추가 후 클라이언트에게 응답 */

    @Override
    public String filterType() {
        /* filter의 타입 정의 (pre, route, post, error) */
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        /* filter 의 실행 순서 정의 */
        return SEND_RESPONSE_FILTER_ORDER - 1;
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

        context.getResponse().addHeader("Trace-ID", UUID.randomUUID().toString());

        return null;
    };
}
