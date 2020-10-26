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
@Api(value = "品牌接口管理页面", description = "品牌接口,提供页面的增删改查")
public interface BrandService {

    @ApiOperation("查询所有品牌数据")
    List<Brand> findAll();

    @ApiOperation("根据id查询品牌")
    Brand findById(Integer id);

    @ApiOperation("添加品牌")
    int add(Brand brand);

    @ApiOperation("修改品牌")
    int update(Brand brand);

    @ApiOperation("根据id删除品牌")
    int deleteById(int id);

    @ApiOperation("根据品牌名或者首字母查询")
    List<Brand> findByConditions(Map<String, Object> conditionsMap);

    @ApiOperation("根据品牌对象查询")
    List<Brand> findByBrand(Brand brand);

    @ApiOperation("分页查询品牌")
    PageResult findPage(int currentPage, int size);
}
