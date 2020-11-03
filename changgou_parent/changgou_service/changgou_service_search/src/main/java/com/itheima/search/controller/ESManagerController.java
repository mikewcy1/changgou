package com.itheima.search.controller;

import com.itheima.common.entity.Result;
import com.itheima.common.entity.StatusCode;
import com.itheima.search.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author 王昌耀
 * @date 2020-11-03 21:01
 */
@RestController
@RequestMapping("/manager")
public class ESManagerController {

    @Autowired
    private EsManagerService esManagerService;

    //创建索引结构
    @GetMapping("/create")
    public Result create() {
        esManagerService.createIndexAndMapping();
        return new Result(true, StatusCode.OK, "创建索引库结构成功");
    }

    //一键导入索引库
    @GetMapping("/importAll")
    public Result importAll() {
        esManagerService.importAll();
        return new Result(true, StatusCode.OK, "导入全部数据成功");
    }
}
