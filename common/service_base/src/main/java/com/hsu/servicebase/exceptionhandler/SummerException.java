package com.hsu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummerException extends RuntimeException{
    private Integer code;       //异常状态码
    private String msg;         //异常提示信息
}
