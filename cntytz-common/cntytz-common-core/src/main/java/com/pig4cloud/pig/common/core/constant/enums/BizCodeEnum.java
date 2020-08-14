package com.pig4cloud.pig.common.core.constant.enums;

import lombok.ToString;

/**
 * @author : songyibo
 * @version : v1.0
 * @description : TODO
 * @date: 2020-06-05 16:06
 */
@ToString
public enum BizCodeEnum implements IErrorCode {

    SUCCESS(0, "success"),

    FAILED(500, "failed"),
    /**
     * 系统未知异常
     */
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    /**
     * 参数格式校验失败
     */
    VAILD_EXCEPTION(10001, "参数格式校验失败"),
    /**
     * 算术运算异常
     */
    ARITHMETIC_EXCEPTION(10002, "算术运算异常"),
    /**
     * 请求流量过大
     */
    TOO_MANY_REQUEST(10003, "请求流量过大"),

    NULL_POINT_EXCEPTION(10004, "空指针异常"),

    ILLEGAL_ARGUMENT_EXCEPTION(10005, "非法参数异常"),

    ARRAY_INDEX_OUT_EXCEPTION(10006, "数组越界异常"),

    INDEX_OUT_EXCEPTION(10007, "索引越界异常"),

    NUMBER_FORMAT_EXCEPTION(10008, "数字格式化异常"),

    PARSE_EXCEPTION(10009, "解析异常"),


    INSUFFICIENT_AUTHORITY(20000, "授权权限不足"),
    INVALID_AUTH_TOKEN(20001, "无效的访问令牌"),
    INVALID_APP_AUTH_TOKEN(20002, "无效的应用授权令牌"),
    APP_AUTH_TOKEN_TIME_OUT(20003, "应用授权令牌已过期"),
    AUTH_TOKEN_TIME_OUT(20004, "访问令牌已过期"),

    ;

    private int code;
    private String msg;

    /**
     * @param code
     * @param msg
     */
    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误码
     *
     * @return
     */
    @Override
    public Integer getCode() {
        return this.code;
    }

    /**
     * 错误描述
     *
     * @return
     */
    @Override
    public String getMsg() {
        return this.msg;
    }}
