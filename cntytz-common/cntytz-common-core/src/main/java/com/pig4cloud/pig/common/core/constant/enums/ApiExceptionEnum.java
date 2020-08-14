package com.pig4cloud.pig.common.core.constant.enums;

import lombok.Getter;

/**
 * @author : songyibo
 * @version : v1.0
 * @description : TODO
 * @date: 2020-06-05 11:13
 */
@Getter
public enum ApiExceptionEnum implements IErrorCode {
    /**
     * 用户注册错误
     */
    USER_REGISTRATION_ERROR(40100, "用户注册错误"),
    /**
     * 用户未同意隐私协议
     */
    USER_NOT_AGREE_THE_PRIVACY_AGREEMENT(40101, "用户未同意隐私协议"),
    /**
     * 注册国家或地区受限
     */
    LIMITED_COUNTRY_REGION_OF_REGISTRATION(40102, "注册国家或地区受限"),
    /**
     * 用户名校验失败
     */
    USER_NAME_VERIFICATION_FAILED(40110, "用户名校验失败"),
    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(40111, "用户名已存在"),
    /**
     * 用户名包含敏感词
     */
    USERNAME_CONTAINS_SENSITIVE_WORDS(40112, "用户名包含敏感词"),
    /**
     * 用户名包含特殊字符
     */
    USERNAME_CONTAINS_SPECIAL_CHARACTERS(40113, "用户名包含特殊字符"),
    /**
     * 密码校验失败
     */
    PASSWORD_VERIFICATION_FAILED(40120, "密码校验失败"),
    /**
     * 密码长度不够
     */
    PASSWORD_LENGTH_IS_NOT_ENOUGH(40121, "密码长度不够"),
    /**
     * 密码强度不够
     */
    PASSWORD_STRENGTH_IS_NOT_ENOUGH(40122, "密码强度不够"),
    /**
     * 校验码输入错误
     */
    CHECK_CODE_IUPUT_ERROR(40130, "校验码输入错误"),
    /**
     * 短信校验码输入错误
     */
    SMS_VERIFICATION_CODE_INPUT_ERROR(40131, "短信校验码输入错误"),

    ;

    private int code;
    private String msg;

    ApiExceptionEnum(int code, String msg) {
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
