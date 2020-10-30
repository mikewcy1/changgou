package com.itheima.system.filter;

import com.itheima.system.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 权鉴过滤器
 *
 * @author 王昌耀
 * @date 2020/10/29 20:49
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取响应
        ServerHttpResponse response = exchange.getResponse();
        //如果请求是登录就放行(/admin/login)
        if (request.getURI().getPath().contains("/admin/login")) {
            return chain.filter(exchange);
        }
        //获取请求头
        HttpHeaders headers = request.getHeaders();
        //从请求头获取token
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        //判断token是否存在
        if (StringUtils.isEmpty(token)) {
            //不存在,没有权限
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //存在,解析token
        try {
            JwtUtil.parseJWT(token);
        }
        //解析失败 token过期或伪造token
        catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //放行
        return chain.filter(exchange);
    }

    //数值越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
