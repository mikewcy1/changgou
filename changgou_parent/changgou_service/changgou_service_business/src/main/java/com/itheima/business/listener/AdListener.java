package com.itheima.business.listener;

import okhttp3.*;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * @author 王昌耀
 * @date 2020-11-03 19:01
 */
@Component
public class AdListener {

    //从rabbitmq中取消息,指定队列
    @RabbitListener(queues = "ad_update_queue")
    public void getMessage(String message) {
        System.out.println("接收到的消息是: " + message);

        //远程请求lua执行更新
        //1.实例化httpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //设置请求路径
        String url = "http://192.168.200.128/ad_update?position=" + message;
        //3.设置请求对象
        Request request = new Request.Builder().url(url).build();
        //2.执行请求

        Call call = okHttpClient.newCall(request);
        //5.返回响应
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("请求成功: " + response.message());
            }
        });
    }
}
