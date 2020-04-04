package com.laozhang.maxweb.base;

/**
 * Base response enum.
 */
@SuppressWarnings("nls")
public enum BaseResponseEnum implements IResponse {

    /**
     * "000000", "SUCCESS"
     */
    SUCCESS("000000", "SUCCESS"),

    /**
     * "100000", "业务错误"
     */
    BUSINESS_EXCEPTION("100000", "业务错误"),

    /**
     * "200000", "参数校验错误"
     */
    PARAMETER_EXCEPTION("200000", "参数校验错误"),

    /**
     * "300000", "权限不足"
     */
    AUTHORITY_EXCEPTION("300000", "权限不足"),

    /**
     * "400000", "系统错误"
     */
    SYSTEM_EXCEPTION("400000", "系统错误");

    private String code;

    private String message;

    BaseResponseEnum(final String code, final String message) {

        this.code = code;
        this.message = message;
    }

    public String getCode() {

        return this.code;
    }

    public String getMessage() {

        return this.message;
    }

    public Object getBody() {

        return null;
    }

}
