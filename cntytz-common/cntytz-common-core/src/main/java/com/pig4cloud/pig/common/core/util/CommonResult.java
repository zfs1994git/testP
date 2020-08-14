package com.pig4cloud.pig.common.core.util;

import com.pig4cloud.pig.common.core.constant.enums.BizCodeEnum;
import com.pig4cloud.pig.common.core.constant.enums.IErrorCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * @author : songyibo
 * @version : v1.0
 * @description : 通用返回对象
 * @date: 2020-06-04 11:25
 */
@Getter
@Setter
@ToString
public class CommonResult<T> {

    private int code;
    private String msg;
    private T data;

    public CommonResult() {
    }

    public CommonResult(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(BizCodeEnum.FAILED);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public CommonResult(T data) {
        this(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMsg(), data);
    }

    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMsg());
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<T>(BizCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> CommonResult<T> error() {
        return new CommonResult<T>(BizCodeEnum.FAILED.getCode(), BizCodeEnum.FAILED.getMsg());
    }

    public static <T> CommonResult<T> error(Exception e) {
        return new CommonResult<T>(BizCodeEnum.FAILED.getCode(), e.getMessage());
    }

    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult<T>(code, message);
    }

    public static <T> CommonResult<T> error(int code, String message, T data) {
        return new CommonResult<T>(code, message, data);
    }
}
