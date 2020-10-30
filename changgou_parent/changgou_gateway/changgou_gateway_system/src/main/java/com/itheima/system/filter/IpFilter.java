package com.itheima.system.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/*
 * @author 王昌耀
 * @date 2020/10/29 18:08
 */
@Component
public class IpFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经过第1个过滤器");
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        //获取IP地址
        System.out.println("ip地址: "+remoteAddress.getHostName());
        return chain.filter(exchange);

    }

    //数值越小,优先级越高
    @Override
    public int getOrder() {
        return 1;
    }
}
