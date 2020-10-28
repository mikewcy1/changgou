package com.itheima.goods.dao;

import com.itheima.goods.pojo.Spec;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:32
 */

public interface SpecMapper extends Mapper<Spec> {

    //根据商品种类名称查询规格
    @Select("select id,name,options,seq,template_id templateId from tb_spec where template_id in (select template_id from tb_category where name = #{categoryName})")
    List<Map>  findByCategoryName(String categoryName);
}
