package com.itheima.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/*
 * @author 王昌耀
 * @date 2020/10/26 16:14
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.itheima.goods.dao")
public class GoodsApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class);
    }
}