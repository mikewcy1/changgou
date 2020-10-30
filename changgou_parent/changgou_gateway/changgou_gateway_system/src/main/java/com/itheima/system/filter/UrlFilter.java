package com.itheima.system.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/*
 * @author 王昌耀
 * @date 2020/10/29 18:16
 */
@Component
public class UrlFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经过第2个过滤器");
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        System.out.println("url: "+ url);
        return chain.filter(exchange);
    }

    //数值越大,优先度越低
    @Override
    public int getOrder() {
        return 2;
    }
}
