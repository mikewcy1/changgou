package com.itheima.search.listener;

import com.itheima.search.config.RabbitmqConfig;
import com.itheima.search.service.EsManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @author 王昌耀
 * @date 2020-11-03 21:45
 */
@Component
public class GoodDownListener {

    @Autowired
    private EsManagerService esManagerService;

    //从rabbitmq取出数据,然后删除
    @RabbitListener(queues = RabbitmqConfig.SEARCH_DEL_QUEUE)
    public void getMessage(String message) {
        System.out.println("删除索引库监听类,接收到的spuId:  " + message);
        //查询
        esManagerService.delDataBySpuId(message);
    }
}
