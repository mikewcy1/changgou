package com.itheima.goods.service;

import com.github.pagehelper.Page;
import com.itheima.goods.pojo.Brand;
import com.itheima.goods.pojo.Spec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:33
 */

public interface SpecService {


    List<Map> findByCategoryName(String categoryName);


    List<Spec> findAll();


    Spec findById(Integer id);


    int save(Spec spec);


    int update(Spec spec);


    int deleteById(Integer id);


    List<Spec> findByConditions(Spec spec);


    Page<Spec> findPage(Spec spec, int currentPage, int pageSize);

    //@ApiOperation("分页查询规格数据")
    Page<Spec> findPage( int currentPage, int pageSize);
}
