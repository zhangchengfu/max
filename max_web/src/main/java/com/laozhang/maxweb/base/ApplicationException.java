package com.laozhang.maxweb.base;


/**
 * Customized application exception.
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -5430955945096857330L;

    private final String code;

    private String message;

    /**
     * default constructor.
     */
    public ApplicationException() {
        super(BaseResponseEnum.SYSTEM_EXCEPTION.getMessage());
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
        this.message = BaseResponseEnum.SYSTEM_EXCEPTION.getMessage();

    }

    /**
     * @param message error message
     */
    public ApplicationException(final String message) {
        super(message);
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
    }

    /**
     * @param code error code
     * @param message error message
     */
    public ApplicationException(final String code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {

        return code;
    }

    @Override
    public String getMessage() {

        return message;
    }

}
