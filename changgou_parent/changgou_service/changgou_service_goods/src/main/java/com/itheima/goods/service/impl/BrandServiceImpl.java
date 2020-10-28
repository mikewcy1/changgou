package com.itheima.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.goods.dao.BrandMapper;
import com.itheima.goods.pojo.Brand;
import com.itheima.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:50
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有品牌
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 根据id查询品牌
     *
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加品牌
     *
     * @param brand
     * @return
     */
    @Override
    public int add(Brand brand) {
        return brandMapper.insert(brand);
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @Override
    public int update(Brand brand) {
        return brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 根据id删除品牌
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(int id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据品牌名或者首字母查询
     *
     * @param conditionsMap
     * @return
     */
    @Override
    public List<Brand> findByConditions(Map<String, Object> conditionsMap) {
        //获取品牌名
        String name = (String) conditionsMap.get("name");
        //获取首字母
        String letter = (String) conditionsMap.get("letter");

        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (conditionsMap != null) {
            //品牌名
            if (name != null && !"".equals(name)) {
                criteria.andLike("name", "%" + name + "%");
            }
            //首字母
            if (letter != null && !"".equals(letter)) {
                criteria.andEqualTo("letter", letter);
            }
        }
        //根据条件查询
        List<Brand> brandList = brandMapper.selectByExample(example);
        return brandList;
    }

    /**
     * 根据品牌对象查询
     *
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findByBrand(Brand brand) {
        //自定义类
        Example example = new Example(Brand.class);
        //用于拼接条件
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            //品牌名
            if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            //品牌对象
            if (brand.getLetter() != null && !"".equals(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }

        List<Brand> brandList = brandMapper.selectByExample(example);
        return brandList;
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页
     * @param size        每页显示条数
     * @return
     */
    @Override
    public PageResult findPage(int currentPage, int size) {
        //使用分业助手
        PageHelper.startPage(currentPage, size);
        Page<Brand> brands = (Page<Brand>) brandMapper.selectAll();
        return new PageResult(brands.getTotal(), brands.getResult());
    }

    /**
     * @param brand       品牌对象
     * @param currentPage 当前页
     * @param size        每页显示条数
     * @return
     */
    @Override
    public PageResult findPage(Brand brand, int currentPage, int size) {
        //设置分业条件
        PageHelper.startPage(currentPage, size);
        //自定义类
        Example example = new Example(Brand.class);
        //用于拼接条件
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            //按品牌名称
            if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            //按首字母
            if (brand.getLetter() != null && !"".equals(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        //制造异常
        //int i = 1/0;
        Page<Brand> brands = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult(brands.getTotal(), brands.getResult());
    }

    /**
     *
     * @param categoryName 商品种类名称
     * @return
     */
    @Override
    public List<Brand> findByCategory(String categoryName) {
        return brandMapper.findByCategory(categoryName);
    }
}
