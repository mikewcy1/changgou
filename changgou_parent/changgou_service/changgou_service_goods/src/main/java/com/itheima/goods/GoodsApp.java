package com.itheima.goods;

import com.itheima.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/*
 * @author 王昌耀
 * @date 2020/10/26 16:14
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.itheima.goods.dao")
@ComponentScan(basePackages = "com.itheima")
@EnableSwagger2 //开启swagger
public class GoodsApp {

    @Value("${workId}")
    private Integer workId;

    @Value("${datacenter}")
    private Integer datacenter;

    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class);
    }

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(workId,datacenter);
    }
}
