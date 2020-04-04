package com.laozhang.maxweb.base;


/**
 * Parameter exception.
 */
public class ParameterException extends ApplicationException {

    private static final long serialVersionUID = 6257945742204588120L;

    /**
     * DEFAULT_PARAM_CODE.
     */
    public static final String DEFAULT_PARAM_CODE = BaseResponseEnum.PARAMETER_EXCEPTION.getCode();

    /**
     * DEFAULT_PARAM_MESSAGE.
     */
    public static final String DEFAULT_PARAM_MESSAGE = BaseResponseEnum.PARAMETER_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public ParameterException() {
        super();
        this.code = DEFAULT_PARAM_CODE;
        this.message = DEFAULT_PARAM_MESSAGE;
    }

    /**
     * @param message error message
     */
    public ParameterException(final String message) {
        super(DEFAULT_PARAM_CODE, message);
        this.code = DEFAULT_PARAM_CODE;
        this.message = message;
    }

    /**
     * @param code error code
     * @param message error message
     */
    public ParameterException(final String code, final String message) {
        super(code, message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {

        return code;
    }

    @Override
    public String getMessage() {

        return message;
    }

}
