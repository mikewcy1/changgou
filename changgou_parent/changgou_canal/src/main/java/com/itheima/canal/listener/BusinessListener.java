package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitmqConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 * @author 王昌耀
 * @date 2020-11-03 18:26
 */
@CanalEventListener
public class BusinessListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 监听数据改变的方法
     * @param eventType 数据类型
     * @param rowData 数据库行数据
     * @ListenPoint 表示监听点
     * schema表示监听的数据库,table表示监听的数据库表
     */
    @ListenPoint(schema = "changgou_business", table = "tb_ad")
    public void adupdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("广告表数据发生变化");
       /* //获取改变前的数据
        rowData.getBeforeColumnsList().forEach(column -> System.out.println("改变前的数据: " + column.getName() + "==" + column.getValue()));

        //获取改变后的数据
        rowData.getAfterColumnsList().forEach(column -> System.out.println("改变后的数据: " + column.getName() + "::" + column.getValue()));*/

        List<CanalEntry.Column> columnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : columnsList) {
            if ("position".equals(column.getName())){
                System.out.println("发送最新数据到MQ:"+column.getValue());
                //发送到mq中
                rabbitTemplate.convertAndSend("", RabbitmqConfig.AD_UPDATE_QUEUE,column.getValue());
            }
        }
    }
}
