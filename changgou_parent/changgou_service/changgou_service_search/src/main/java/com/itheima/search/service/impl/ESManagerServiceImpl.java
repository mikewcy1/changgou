package com.itheima.search.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.goods.feign.SkuFeign;
import com.itheima.goods.pojo.Sku;
import com.itheima.search.dao.ESManagerMapper;
import com.itheima.search.pojo.SkuInfo;
import com.itheima.search.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020-11-03 20:45
 */
@Service
public class ESManagerServiceImpl implements EsManagerService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private ESManagerMapper esManagerMapper;

    //创建索引库结构
    @Override
    public void createIndexAndMapping() {
        //创建索引
        elasticsearchTemplate.createIndex(SkuInfo.class);
        //创建映射
        elasticsearchTemplate.putMapping(SkuInfo.class);
    }

    //一键导入索引库
    @Override
    public void importAll() {
        //远程调用skuFeign里的方法
        List<Sku> skuList = skuFeign.findSkuListBySpuId("all");
        if (skuList == null || skuList.size() < 0) {
            throw new RuntimeException("当前没有数据被查到,无法导入索引库");
        }
        //skuList转换为json
        String jsonSkuStr = JSON.toJSONString(skuList);
        //将json转换为skuInfo
        List<SkuInfo> skuInfoList = JSON.parseArray(jsonSkuStr, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            Map specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(specMap);
        }
        esManagerMapper.saveAll(skuInfoList);
    }


    //根据spuId同步数据到索引库中
    @Override
    public void importDataToESBySpuId(String spuId) {
        //远程调用skuFeign的方法
        List<Sku> skuList = skuFeign.findSkuListBySpuId(spuId);
        if (skuList == null || skuList.size() < 0) {
            throw new RuntimeException("当前没有数据被查到,无法导入索引库");
        }
        //skuList转换为json
        String jsonSkuStr = JSON.toJSONString(skuList);
        //将json转换为skuInfo
        List<SkuInfo> skuInfoList = JSON.parseArray(jsonSkuStr, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            Map specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(specMap);
        }
        esManagerMapper.saveAll(skuInfoList);
    }

    //根据souid删除es索引库中相关的sku数据
    @Override
    public void delDataBySpuId(String spuId) {
        //远程调用skuFeign的方法
        List<Sku> skuList = skuFeign.findSkuListBySpuId(spuId);
        if (skuList == null || skuList.size() < 0) {
            throw new RuntimeException("当前没有数据被查到,无法导入索引库");
        }
        //循环删除
        for (Sku sku : skuList) {
            esManagerMapper.deleteById(Long.parseLong(sku.getId()));
        }
    }
}
