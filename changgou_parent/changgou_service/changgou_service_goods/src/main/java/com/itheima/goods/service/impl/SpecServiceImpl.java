package com.itheima.goods.service.impl;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:38
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.goods.dao.SpecMapper;
import com.itheima.goods.pojo.Spec;
import com.itheima.goods.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    /**
     * 根据商品种类名称查询规格
     *
     * @param categoryName
     * @return
     */
    @Override
    public List<Map> findByCategoryName(String categoryName) {
        List<Map> mapList = specMapper.findByCategoryName(categoryName);
        for (Map map : mapList) {
            String optionsStr = (String) map.get("options");
            String[] options = optionsStr.split(",");
            map.put("options", options);
        }
        return mapList;
    }

    /**
     * 查询所有规格
     *
     * @return
     */
    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    /**
     * 根据id查询所有的规格
     *
     * @param id
     * @return
     */
    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加规格
     *
     * @param spec
     * @return
     */
    @Override
    public int save(Spec spec) {
        return specMapper.insert(spec);
    }

    /**
     * 修改规格
     *
     * @param spec
     * @return
     */
    @Override
    public int update(Spec spec) {
        return specMapper.updateByPrimaryKey(spec);
    }

    /**
     * 删除规格
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        return specMapper.deleteByPrimaryKey(id);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param spec
     * @return
     */
    @Override
    public List<Spec> findByConditions(Spec spec) {
        return specMapper.selectByExample(creatExample(spec));
    }

    /**
     * 多条件分页查询
     *
     * @param spec
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Spec> findPage(Spec spec, int currentPage, int pageSize) {
        //设置分页数据
        PageHelper.startPage(currentPage, pageSize);
        //调用公共方法
        Example example = creatExample(spec);
        Page<Spec> specList = (Page<Spec>) specMapper.selectByExample(example);
        return specList;
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Spec> findPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<Spec>) specMapper.selectAll();
    }

    //抽出相同的方法
    public Example creatExample(Spec spec) {
        //自定义类
        Example example = new Example(Spec.class);
        //用于拼接条件
        Example.Criteria criteria = example.createCriteria();
        if (spec != null) {
            if (spec.getName() != null && !"".equals(spec.getName())) {
                criteria.andLike("name", "%" + spec.getName() + "%");
            }
            if (spec.getOptions() != null && !"".equals(spec.getOptions())) {
                criteria.andLike("options", "%" + spec.getOptions() + "%");
            }
            if (spec.getId() != null && !"".equals(spec.getId())) {
                criteria.andEqualTo("id", spec.getId());
            }
            if (spec.getTemplateId() != null && !"".equals(spec.getTemplateId())) {
                criteria.andEqualTo("templateId", spec.getTemplateId());
            }
            if (spec.getSeq() != null && !"".equals(spec.getSeq())) {
                criteria.andEqualTo("seq", spec.getSeq());
            }
        }
        return example;
    }
}
