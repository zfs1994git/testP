package com.pig4cloud.pig.common.core.exception;

import com.pig4cloud.pig.common.core.constant.enums.BizCodeEnum;
import com.pig4cloud.pig.common.core.util.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : songyibo
 * @version : v1.0
 * @description : 统一异常处理器
 * @date: 2020-06-05 15:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 捕捉 @RequestBody 类型的入参
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleVaildException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>(16);
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return CommonResult.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg(), errorMap);
    }

    /**
     * 捕捉 非@RequestBody 类型的入参
     *
     * @param e
     * @return TODO：org.springframework.http.converter.HttpMessageNotReadableException: Required request body is missing: public com.cntytz.common.utils.R com.cntytz.demoa.controller.SysUserController.save(com.cntytz.demoa.entity.SysUserEntity)
     */
    @ExceptionHandler(value = BindException.class)
    public CommonResult BindException(BindException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return CommonResult.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg(), errorMap);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public CommonResult handleVaildException(ArithmeticException e) {
        log.error("算术运算出现问题{}，异常类型：{},\n栈追踪：{}", e.getMessage(), e.getClass(), e.getStackTrace());
        String message = e.getMessage();
        return CommonResult.error(BizCodeEnum.ARITHMETIC_EXCEPTION.getCode(), BizCodeEnum.ARITHMETIC_EXCEPTION.getMsg(), message);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public CommonResult nullPointException(NullPointerException e) {
        log.error("空指针异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(BizCodeEnum.NULL_POINT_EXCEPTION.getCode(), BizCodeEnum.NULL_POINT_EXCEPTION.getMsg(), e.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public CommonResult illegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(BizCodeEnum.ILLEGAL_ARGUMENT_EXCEPTION.getCode(), BizCodeEnum.ILLEGAL_ARGUMENT_EXCEPTION.getMsg(), e.getMessage());
    }

    @ExceptionHandler(value = {ArrayIndexOutOfBoundsException.class})
    public CommonResult arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        log.error("数组越界异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(BizCodeEnum.ARRAY_INDEX_OUT_EXCEPTION.getCode(), BizCodeEnum.ARRAY_INDEX_OUT_EXCEPTION.getMsg(), e.getMessage());
    }

    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    public CommonResult indexOutOfBoundsException(IndexOutOfBoundsException e) {
        log.error("索引越界异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(BizCodeEnum.INDEX_OUT_EXCEPTION.getCode(), BizCodeEnum.INDEX_OUT_EXCEPTION.getMsg(), e.getMessage());
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public CommonResult numberFormatException(NumberFormatException e) {
        log.error("数字格式化异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(BizCodeEnum.NUMBER_FORMAT_EXCEPTION.getCode(), BizCodeEnum.NUMBER_FORMAT_EXCEPTION.getMsg(), e.getMessage());
    }

    /**
     * 自定义异常捕捉器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult ApiException(ApiException e) {
        log.error("自定义异常, errMsg :{}, stack off :{}", e.getMessage(), e.getStackTrace());
        return CommonResult.error(e.getiErrorCode().getCode(), e.getiErrorCode().getMsg());
    }

    /**
     * 兜底异常处理
     *
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public CommonResult handleException(Throwable throwable) {
        log.error("错误：{}", throwable);
        return CommonResult.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
