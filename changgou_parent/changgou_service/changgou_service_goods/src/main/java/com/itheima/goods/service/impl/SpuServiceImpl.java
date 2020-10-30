package com.itheima.goods.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.util.IdWorker;
import com.itheima.goods.dao.*;
import com.itheima.goods.pojo.*;
import com.itheima.goods.service.SpuService;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     *
     * @param goods
     */
    @Transactional
    @Override
    public void add(Goods goods) {
        //添加spu
        Spu spu = goods.getSpu();
        //设置分布式id
        long spuId = idWorker.nextId();
        spu.setId(String.valueOf(spuId));
        //设置删除状态
        spu.setIsDelete("0");
        //设置上架状态
        spu.setIsMarketable("0");
        //设置审核状态
        spu.setStatus("0");
        spuMapper.insertSelective(spu);

        //添加sku集合
        this.saveSkuList(goods);
    }

    //添加sku数据
    private void saveSkuList(Goods goods) {

        //获取spu对象
        Spu spu = goods.getSpu();
        //获取category对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        //获取brand对象
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        //设置品牌与分类的关联信息
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(category.getId());
        categoryBrand.setBrandId(brand.getId());
        //查询中间表是否有记录
        int count = categoryBrandMapper.selectCount(categoryBrand);
        if (count == 0) {
            categoryBrandMapper.insert(categoryBrand);
        }

        // 获取sku对象
        List<Sku> skuList = goods.getSkuList();
        //判断
        if (skuList != null && skuList.size() > 0) {
            for (Sku sku : skuList) {
                //设置skuId
                sku.setId(String.valueOf(idWorker.nextId()));
                //设置sku规格
                if (StringUtils.isEmpty(sku.getSpec())) {
                    sku.setSpec("{}");
                }
                //设置sku名称,sku名称=spu+规格
                String name = spu.getName();
                Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                if (specMap != null && specMap.size() > 0) {
                    for (String value : specMap.values()) {
                        name += " " + value;
                    }
                }
                sku.setName(name);
                //设置spuId
                sku.setSpuId(spu.getId());
                //设置创建和修改时间
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                //设置categoryId和categoryName
                sku.setCategoryId(category.getId());
                sku.setCategoryName(category.getName());
                //设置品牌名称brandName
                sku.setBrandName(brand.getName());
                //循环添加到数据库
                skuMapper.insertSelective(sku);
            }
        }
    }


    /**
     * 修改
     *
     * @param goods
     */
    @Transactional
    @Override
    public void update(Goods goods) {
        //修改spu
        Spu spu = goods.getSpu();
        spuMapper.updateByPrimaryKeySelective(spu);

        //修改sku
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(spu.getId())){
            throw new RuntimeException("id必填且为字符串");
        }
        criteria.andEqualTo("spuId", spu.getId());
        //删除记录
        skuMapper.deleteByExample(example);

        //重新添加
        saveSkuList(goods);
    }


    /**
     * 逻辑删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        //获取spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //必须先下架才能删除
        if (!"0".equals(spu.getIsMarketable())) {
            throw new RuntimeException("必须先下架才能删除");
        }
        //设置删除状态
        spu.setIsDelete("1");
        //设置审核状态为未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Spu>) spuMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Spu>) spuMapper.selectByExample(example);
    }

    /**
     * 根据id查询goods
     *
     * @param id
     * @return
     */
    @Override
    public Goods findGoodsById(String id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //查询sku
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);

        //封装Goods
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /**
     * 商品审核
     *
     * @param id
     */
    @Transactional
    @Override
    public void audit(String id) {
        //查询spu对象
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //判断商品是否存在
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }
        //判断商品是否被删除
        if ("1".equals(spu.getIsDelete())) {
            throw new RuntimeException("商品已被删除");
        }
        //状态为1,表示通过审核
        spu.setStatus("1");
        //自动上架
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品下架
     *
     * @param id
     */
    @Transactional
    @Override
    public void pull(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //判断商品是否存在
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }
        //判断商品是否被删除
        if ("1".equals(spu.getIsDelete())) {
            throw new RuntimeException("商品已被删除");
        }
        //设置下架
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 上架商品
     *
     * @param id
     */
    @Transactional
    @Override
    public void put(String id) {
        //查询spu对象
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getStatus())) {
            throw new RuntimeException("未通过审核的商品不能上架");
        }
        //设置上架状态
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 还原逻辑删除的方法
     *
     * @param id
     */
    @Transactional
    @Override
    public void restore(String id) {
        //获取spu对象
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("未被删除的商品不需要还原");
        }
        //设置删除状态为未删除
        spu.setIsDelete("0");
        //设置审核状态为未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 物理删除
     *
     * @param id
     */
    @Transactional
    @Override
    public void realDelete(String id) {
        //获取spu对象
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("此商品未被删除");
        }
        //删除商品
        spuMapper.deleteByPrimaryKey(id);
    }


    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andEqualTo("sn", searchMap.get("sn"));
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 副标题
            if (searchMap.get("caption") != null && !"".equals(searchMap.get("caption"))) {
                criteria.andLike("caption", "%" + searchMap.get("caption") + "%");
            }
            // 图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("images") != null && !"".equals(searchMap.get("images"))) {
                criteria.andLike("images", "%" + searchMap.get("images") + "%");
            }
            // 售后服务
            if (searchMap.get("saleService") != null && !"".equals(searchMap.get("saleService"))) {
                criteria.andLike("saleService", "%" + searchMap.get("saleService") + "%");
            }
            // 介绍
            if (searchMap.get("introduction") != null && !"".equals(searchMap.get("introduction"))) {
                criteria.andLike("introduction", "%" + searchMap.get("introduction") + "%");
            }
            // 规格列表
            if (searchMap.get("specItems") != null && !"".equals(searchMap.get("specItems"))) {
                criteria.andLike("specItems", "%" + searchMap.get("specItems") + "%");
            }
            // 参数列表
            if (searchMap.get("paraItems") != null && !"".equals(searchMap.get("paraItems"))) {
                criteria.andLike("paraItems", "%" + searchMap.get("paraItems") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andEqualTo("isMarketable", searchMap.get("isMarketable"));
            }
            // 是否启用规格
            if (searchMap.get("isEnableSpec") != null && !"".equals(searchMap.get("isEnableSpec"))) {
                criteria.andEqualTo("isEnableSpec", searchMap.get("isEnableSpec"));
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andEqualTo("isDelete", searchMap.get("isDelete"));
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }

            // 品牌ID
            if (searchMap.get("brandId") != null) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
            // 一级分类
            if (searchMap.get("category1Id") != null) {
                criteria.andEqualTo("category1Id", searchMap.get("category1Id"));
            }
            // 二级分类
            if (searchMap.get("category2Id") != null) {
                criteria.andEqualTo("category2Id", searchMap.get("category2Id"));
            }
            // 三级分类
            if (searchMap.get("category3Id") != null) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
            // 运费模板id
            if (searchMap.get("freightId") != null) {
                criteria.andEqualTo("freightId", searchMap.get("freightId"));
            }
            // 销量
            if (searchMap.get("saleNum") != null) {
                criteria.andEqualTo("saleNum", searchMap.get("saleNum"));
            }
            // 评论数
            if (searchMap.get("commentNum") != null) {
                criteria.andEqualTo("commentNum", searchMap.get("commentNum"));
            }

        }
        return example;
    }

}
