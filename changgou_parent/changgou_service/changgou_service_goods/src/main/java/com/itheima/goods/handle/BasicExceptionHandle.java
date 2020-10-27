package com.itheima.goods.handle;

import com.itheima.common.entity.Result;
import com.itheima.common.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @author 王昌耀
 * @date 2020/10/27 11:33
 */
@ControllerAdvice //当前类是一个增强类
public class BasicExceptionHandle {

    @ExceptionHandler(Exception.class) //当前方法是一个异常处理方法,处理整个Exception类
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "当前系统繁忙,请稍后再试");
    }
}
