package com.itheima.canal.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @author 王昌耀
 * @date 2020-11-03 18:41
 */
@Configuration
public class RabbitmqConfig {

    //定义队列名称
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";
    //定义上架队列名称
    public static final String SEARCH_ADD_QUEUE = "search_add_queue";
    //定义上架交换机
    public static final String GOODS_UP_EXCHANGE = "goods_up_exchange";

    //定义下架队列
    public static final String SEARCH_DEL_QUEUE="search_del_queue";
    //定义下架交换机
    public static final String GOODS_DOWN_EXCHANGE="goods_down_exchange";

    //声明队列
    @Bean
    public Queue queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

    //声明上架队列
    @Bean(SEARCH_ADD_QUEUE)
    public Queue search_add_queue() {
        return new Queue(SEARCH_ADD_QUEUE);
    }

    //声明下架队列
    @Bean(SEARCH_DEL_QUEUE)
    public Queue SEARCH_DEL_QUEUE() {
        return new Queue(SEARCH_DEL_QUEUE);
    }

    //声明上架交换机
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange goods_up_exchange() {
        //使用发布订阅模式fanout
        return ExchangeBuilder.fanoutExchange(GOODS_UP_EXCHANGE).durable(true).build();
    }

    //声明下架交换机
    @Bean(GOODS_DOWN_EXCHANGE)
    public Exchange GOODS_DOWN_EXCHANGE() {
        //使用发布订阅模式fanout
        return ExchangeBuilder.fanoutExchange(GOODS_DOWN_EXCHANGE).durable(true).build();
    }


    //绑定上架队列到上架交换机
    @Bean
    public Binding AD_UPDATE_QUEUE_BINDING(@Qualifier(SEARCH_DEL_QUEUE) Queue queue,@Qualifier(GOODS_UP_EXCHANGE) Exchange exchange){
        //不适用routinkey路由key
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    //绑定下架队列到下架交换机
    @Bean
    public Binding GOODS_DOWN_EXCHANGE_BINDING(@Qualifier(SEARCH_ADD_QUEUE) Queue queue,@Qualifier(GOODS_DOWN_EXCHANGE) Exchange exchange){
        //不适用routinkey路由key
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
