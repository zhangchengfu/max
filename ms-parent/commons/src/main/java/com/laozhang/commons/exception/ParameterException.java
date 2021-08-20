package com.laozhang.commons.exception;

import com.laozhang.commons.constant.ApiConstant;
import lombok.Data;

/**
 * 全局异常类
 */
@Data
public class ParameterException extends RuntimeException{

    private Integer errorCode;

    public ParameterException() {
        super(ApiConstant.ERROR_MESSAGE);
        this.errorCode = ApiConstant.ERROR_CODE;
    }

    public ParameterException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ParameterException(String message) {
        super(message);
        this.errorCode = ApiConstant.ERROR_CODE;
    }

    public ParameterException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
