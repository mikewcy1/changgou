package com.itheima.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 * @author 王昌耀
 * @date 2020-11-03 20:01
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.itheima.goods.feign")
public class SearchApp {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApp.class);
    }
}
