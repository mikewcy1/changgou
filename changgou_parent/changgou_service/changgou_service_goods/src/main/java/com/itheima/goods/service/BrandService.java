package com.itheima.goods.service;

import com.itheima.common.entity.PageResult;
import com.itheima.goods.pojo.Brand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:50
 */

public interface BrandService {


    List<Brand> findAll();


    Brand findById(Integer id);


    int add(Brand brand);


    int update(Brand brand);


    int deleteById(int id);


    List<Brand> findByConditions(Map<String, Object> conditionsMap);


    List<Brand> findByBrand(Brand brand);


    PageResult findPage(int currentPage, int size);


    PageResult findPage(Brand brand,int currentPage, int size);


    List<Brand> findByCategory(String categoryName);
}
