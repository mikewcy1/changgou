package com.itheima.search.listener;

/*
 * @author 王昌耀
 * @date 2020-11-03 21:15
 */

import com.itheima.search.config.RabbitmqConfig;
import com.itheima.search.dao.ESManagerMapper;
import com.itheima.search.service.EsManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsUpListener {

    @Autowired
    private EsManagerService esManagerService;
    @RabbitListener(queues = RabbitmqConfig.SEARCH_ADD_QUEUE)
    public void getMessage(String message){
        System.out.println("接收到的消息: "+message);
        //查询
        esManagerService.importDataToESBySpuId(message);
    }
}
