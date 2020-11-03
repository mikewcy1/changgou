package com.itheima.search.service;

/*
 * @author 王昌耀
 * @date 2020-11-03 20:43
 */
public interface EsManagerService {

    //创建索引库结构
    void createIndexAndMapping();

    //导入全部数据到ES索引库
    void importAll();

    //根据spuid导入数据到ES索引库
    void importDataToESBySpuId(String spuId);

    //根据souid删除es索引库中相关的sku数据
     void delDataBySpuId(String spuId);
}
