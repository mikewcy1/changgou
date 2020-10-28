package com.itheima.goods.controller;

import com.github.pagehelper.Page;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.Result;
import com.itheima.common.entity.StatusCode;
import com.itheima.goods.pojo.Spec;
import com.itheima.goods.service.SpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:45
 */
@CrossOrigin
@RestController
@RequestMapping("/spec")
@Api(tags="规格管理",value = "商品规格接口管理页面", description = "商品规格接口,提供页面的增删改查")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 分页+多条件查询
     * @param spec
     * @param currentPage 当前页
     * @param pageSize  每页显示条数
     * @return
     */
    @ApiOperation("分页或者多条件查询规格数据")
    @GetMapping("/findPage/{currentPage}/{pageSize}")
    public Result findPageByConditions(Spec spec, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        Page<Spec> specPage = specService.findPage(spec, currentPage, pageSize);
        if (specPage != null) {
            return new Result(true, StatusCode.OK, "查询成功", new PageResult(specPage.getTotal(), specPage.getResult()));
        }
        return new Result(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 多条件查询
     *
     * @param spec
     * @return
     */
    @ApiOperation("多条件搜索品牌数据")
    @GetMapping("/findByConditions")
    public Result findByConditions(Spec spec) {
        List<Spec> specList = specService.findByConditions(spec);
        if (specList != null && !specList.isEmpty()) {
            return new Result(true, StatusCode.OK, "查询规格成功", specList);
        }
        return new Result(false, StatusCode.ERROR, "查询规格失败");
    }


    /**
     * 根据id删除规格
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id删除规格")
    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        int count = specService.deleteById(id);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(true, StatusCode.ERROR, "删除失败");
    }


    /**
     * 修改规格
     *
     * @param spec
     * @return
     */
    @ApiOperation("修改规格")
    @PutMapping("/update")
    public Result update(@RequestBody Spec spec) {
        int count = specService.update(spec);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "修改成功");
        }
        return new Result(true, StatusCode.ERROR, "修改失败");
    }

    /**
     * 添加规格
     *
     * @param spec
     * @return
     */
    @ApiOperation("添加规格")
    @PostMapping("/add")
    public Result add(@RequestBody Spec spec) {
        int count = specService.save(spec);
        if (count > 0) {
            return new Result(true, StatusCode.OK, "添加成功");
        }
        return new Result(true, StatusCode.ERROR, "添加失败");
    }

    /**
     * 查询所有规格
     *
     * @return
     */
    @ApiOperation("查询所有的规格")
    @GetMapping("/findAll")
    public Result findAll() {
        List<Spec> specList = specService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", specList);
    }


    /**
     * 根据id查询规格
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询所有的规格")
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Spec spec = specService.findById(id);
        if (spec != null) {
            return new Result(true, StatusCode.OK, "查询规格成功", spec);
        }
        return new Result(false, StatusCode.ERROR, "查询规格失败");
    }


    /**
     * 根据种类名称查询规格
     *
     * @param categoryName
     * @return
     */
    @ApiOperation("根据种类名称查询规格")
    @GetMapping("/findByCategoryName/{categoryName}")
    public Result findSpecByCategoryName(@PathVariable("categoryName") String categoryName) {
        List<Map> specList = specService.findByCategoryName(categoryName);
        if (specList != null && !specList.isEmpty()) {
            return new Result(true, StatusCode.OK, "查询规格成功", specList);
        }
        return new Result(false, StatusCode.ERROR, "查询规格失败");
    }


}
