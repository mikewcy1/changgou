package com.itheima.search.dao;

import com.itheima.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/*
 * @author 王昌耀
 * @date 2020-11-03 20:42
 */
public interface ESManagerMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
