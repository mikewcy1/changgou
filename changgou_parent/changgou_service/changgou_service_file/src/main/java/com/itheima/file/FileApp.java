package com.itheima.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * @author 王昌耀
 * @date 2020/10/28 20:24
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.itheima")
@EnableSwagger2 //开启swagger
public class FileApp {
    public static void main(String[] args) {
        SpringApplication.run(FileApp.class);
    }
}
