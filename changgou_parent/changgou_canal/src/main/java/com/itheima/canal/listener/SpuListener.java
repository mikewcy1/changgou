package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitmqConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020-11-03 19:37
 */
@CanalEventListener
public class SpuListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ListenPoint(schema = "changgou_goods", table = "tb_spu")    //监听点
    public void GoodsUp(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //获取改变之前的数据
        Map oldMap = new HashMap();
        rowData.getBeforeColumnsList().forEach(column -> oldMap.put(column.getName(), column.getValue()));

        //获取改变之后的数据
        Map newMap = new HashMap();
        rowData.getAfterColumnsList().forEach(column -> newMap.put(column.getName(), column.getValue()));

        //表示刚刚上架,状态0->1
        if ("0".equals(oldMap.get("is_marketable")) && "1".equals(newMap.get("is_marketable"))){
            //发送spuid到rabbitmq
            rabbitTemplate.convertAndSend(RabbitmqConfig.GOODS_UP_EXCHANGE,"",newMap.get("id"));
        }

        //获取最新下架的商品 状态1->0
        if ("1".equals(oldMap.get("is_marketable")) && "0".equals(newMap.get("is_marketable"))){
            //发送spuid到rabbitmq
            rabbitTemplate.convertAndSend(RabbitmqConfig.GOODS_DOWN_EXCHANGE,"",newMap.get("id"));
        }
    }
}

