package com.hsu.servicebase.exceptionhandler;

import com.hsu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }

    @ExceptionHandler(SummerException.class)
    public R error(SummerException e) {
        e.printStackTrace();
        log.error(e.getMsg()); // 将错误信息输出到日志
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
