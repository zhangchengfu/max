package com.laozhang.maxweb.base;


/**
 * Business exception.
 */
public class AuthorityException extends ApplicationException {

    private static final long serialVersionUID = -3346733232647550691L;

    /**
     * DEFAULT_BUSINESS_CODE.
     */
    public static final String DEFAULT_BUSINESS_CODE = BaseResponseEnum.AUTHORITY_EXCEPTION.getCode();

    /**
     * DEFAULT_BUSINESS_MESSAGE.
     */
    public static final String DEFAULT_BUSINESS_MESSAGE = BaseResponseEnum.AUTHORITY_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public AuthorityException() {
        super();
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = DEFAULT_BUSINESS_MESSAGE;
    }

    /**
     * @param message error message
     */
    public AuthorityException(final String message) {
        super(DEFAULT_BUSINESS_CODE, message);
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = message;
    }

    /**
     * @param code error code
     * @param message error message
     */
    public AuthorityException(final String code, final String message) {
        super(code, message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param response Class or enum that implememts IResponse
     */
    public AuthorityException(final IResponse response) {
        super(response.getCode(), response.getMessage());
        this.code = response.getCode();
        this.message = response.getMessage();
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
