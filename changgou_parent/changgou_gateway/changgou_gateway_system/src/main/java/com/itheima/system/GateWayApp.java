package com.itheima.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
 * @author 王昌耀
 * @date 2020/10/29 14:22
 */
@SpringBootApplication
public class GateWayApp {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApp.class);
    }

    //限流
    @Bean
    public KeyResolver ipKeyResolver(){
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }
        };
    }
}
