package com.itheima.goods.service;

import com.github.pagehelper.Page;
import com.itheima.goods.pojo.Brand;
import com.itheima.goods.pojo.Spec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:33
 */
@Api(value = "商品规格接口管理页面", description = "商品规格接口,提供页面的增删改查")
public interface SpecService {

    @ApiOperation("根据种类名称查询规格")
    List<Spec> findByCategoryName(String categoryName);

    @ApiOperation("查询所有的规格")
    List<Spec> findAll();

    @ApiOperation("根据id查询所有的规格")
    Spec findById(Integer id);

    @ApiOperation("添加规格")
    int save(Spec spec);

    @ApiOperation("修改规格")
    int update(Spec spec);

    @ApiOperation("根据id删除规格")
    int deleteById(Integer id);

    @ApiOperation("多条件搜索品牌数据")
    List<Spec> findByConditions(Spec spec);

    @ApiOperation("分页多条件查询规格数据")
    Page<Spec> findPage(Spec spec, int currentPage, int pageSize);

    @ApiOperation("分页查询规格数据")
    Page<Spec> findPage( int currentPage, int pageSize);
}
