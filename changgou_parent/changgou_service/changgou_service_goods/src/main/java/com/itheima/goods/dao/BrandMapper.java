package com.itheima.goods.dao;

import com.itheima.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:48
 */
public interface BrandMapper extends Mapper<Brand> {

    //根据商品种类查询品牌
    @Select("select * from tb_brand where id in (select brand_id from tb_category_brand tcb WHERE category_id in (select id from tb_category where name = '手机')  )")
    public List<Brand> findByCategory(String categoryName);
}
