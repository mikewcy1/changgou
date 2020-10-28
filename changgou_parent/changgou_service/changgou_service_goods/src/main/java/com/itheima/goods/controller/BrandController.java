package com.itheima.goods.controller;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.Result;
import com.itheima.common.entity.StatusCode;
import com.itheima.goods.pojo.Brand;
import com.itheima.goods.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:51
 */
@CrossOrigin
@RestController
@RequestMapping("/brand")
@Api(tags="品牌管理",value = "品牌接口管理页面", description = "品牌接口,提供页面的增删改查")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //查询所有品牌
    @ApiOperation("查询所有品牌数据")
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", brandList);
    }

    //根据id查询品牌
    @ApiOperation("根据id查询品牌")
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        if (brand == null) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", brand);
    }

    //新增品牌
    @ApiOperation("添加品牌")
    @PostMapping("/add")
    public Result add(@RequestBody Brand brand) {
        int count = brandService.add(brand);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "添加成功");
        }
        return new Result(false, StatusCode.ERROR, "添加失败");
    }

    //修改品牌
    @ApiOperation("修改品牌")
    @PutMapping("/update")
    public Result update(@RequestBody Brand brand) {
        int count = brandService.update(brand);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "修改成功");
        }
        return new Result(false, StatusCode.ERROR, "修改失败");
    }

    //根据id删除品牌
    @ApiOperation("根据id删除品牌")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") int id) {
        int count = brandService.deleteById(id);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(false, StatusCode.ERROR, "删除失败");
    }


    //根据品牌名或品牌首字母查询
    @ApiOperation("根据品牌名或者首字母查询")
    @GetMapping("/findByConditions")
    public Result findByConditions(@RequestParam Map<String, Object> conditionsMap) {
        List<Brand> brandList = brandService.findByConditions(conditionsMap);
        if (brandList != null) {
            return new Result(true, StatusCode.OK, "条件查询成功", brandList);
        }
        return new Result(false, StatusCode.ERROR, "条件查询失败");
    }

    //根据品牌对象查询
    @ApiOperation("根据品牌对象查询")
    @GetMapping("/findByBrand")
    public Result findByBrand(Brand brand) {
        List<Brand> brandList = brandService.findByBrand(brand);
        if (brandList != null && !brandList.isEmpty()) {
            return new Result(true, StatusCode.OK, "条件查询成功", brandList);
        }
        return new Result(false, StatusCode.ERROR, "条件查询失败");
    }

    /*//分页查询品牌数据
    @ApiOperation("分页查询品牌")
    @GetMapping("/findPage/{currentPage}/{size}")
    public PageResult findPage(@PathVariable("currentPage") int currentPage, @PathVariable("size") int size) {
        return brandService.findPage(currentPage, size);
    }*/

    //分页查询品牌数据
    @ApiOperation("条件并且分页查询")
    @GetMapping("/findPage/{currentPage}/{size}")
    public Result findPageAndConditions(Brand brand, @PathVariable("currentPage") int currentPage, @PathVariable("size") int size) {
        PageResult page = brandService.findPage(brand, currentPage, size);
        if (page != null) {
            return new Result(true, StatusCode.OK, "分页条件查询成功", page);
        }
        return new Result(false, StatusCode.ERROR, "分页条件查询失败");
    }

    //根据商品种类查询品牌数据
    @ApiOperation("根据商品种类查询品牌")
    @GetMapping("/findByCategory/{category}")
    public Result findByCategory(@PathVariable String category) {
        List<Brand> brandList = brandService.findByCategory(category);
        if (brandList != null && !brandList.isEmpty()) {
            return new Result(true, StatusCode.OK, "根据商品种类查询成功", brandList);
        }
        return new Result(false, StatusCode.ERROR, "根据商品种类查询失败");
    }
}
