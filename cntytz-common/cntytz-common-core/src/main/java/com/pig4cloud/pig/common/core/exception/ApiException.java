package com.pig4cloud.pig.common.core.exception;

import com.pig4cloud.pig.common.core.constant.enums.IErrorCode;

/**
 * @author : songyibo
 * @version : v1.0
 * @description : TODO
 * @date: 2020-06-05 16:02
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -8977690602050562978L;
    private IErrorCode iErrorCode;


    public ApiException(IErrorCode iErrorCode) {
        this.iErrorCode = iErrorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, IErrorCode iErrorCode) {
        super(message);
        this.iErrorCode = iErrorCode;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(Throwable cause, IErrorCode iErrorCode) {
        super(cause);
        this.iErrorCode = iErrorCode;
    }

    public IErrorCode getiErrorCode() {
        return iErrorCode;
    }
}
