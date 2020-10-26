package com.itheima.goods.controller;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.Result;
import com.itheima.common.entity.StatusCode;
import com.itheima.goods.pojo.Brand;
import com.itheima.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:51
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //查询所有品牌
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", brandList);
    }

    //根据id查询品牌
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        if (brand == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", brand);
    }

    //新增品牌
    @PostMapping("/add")
    public Result add(@RequestBody Brand brand) {
        int count = brandService.add(brand);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "添加成功");
        }
        return new Result(false, StatusCode.ERROR, "添加失败");
    }

    //修改品牌
    @PutMapping("/update")
    public Result update(@RequestBody Brand brand) {
        int count = brandService.update(brand);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "修改成功");
        }
        return new Result(false, StatusCode.ERROR, "修改失败");
    }

    //根据id删除品牌
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") int id) {
        int count = brandService.deleteById(id);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(false, StatusCode.ERROR, "删除失败");
    }


    //根据品牌名或品牌首字母查询
    @GetMapping("/findByConditions")
    public Result findByConditions(@RequestParam Map<String,Object> conditionsMap) {
        List<Brand> brandList = brandService.findByConditions(conditionsMap);
        if (brandList != null) {
            return new Result(true, StatusCode.OK, "条件查询成功",brandList);
        }
        return new Result(false, StatusCode.ERROR, "条件查询失败");
    }

    //根据品牌对象查询
    @GetMapping("/findByBrand")
    public Result findByBrand( Brand brand) {
        List<Brand> brandList = brandService.findByBrand(brand);
        if (brandList != null) {
            return new Result(true, StatusCode.OK, "条件查询成功",brandList);
        }
        return new Result(false, StatusCode.ERROR, "条件查询失败");
    }

    //分页查询品牌数据
    @GetMapping("/findPage/{currentPage}/{size}")
    public PageResult findPage(@PathVariable("currentPage") int currentPage,@PathVariable("size") int size){
        return brandService.findPage(currentPage, size);
    }
}
