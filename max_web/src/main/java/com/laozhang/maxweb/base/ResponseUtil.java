package com.laozhang.maxweb.base;


public final class ResponseUtil {

    /**
     * private constructor.
     */
    private ResponseUtil() {

    }

    /**
     * Wrap null response body of success.
     *
     * @return ResponseDto
     */
    public static ResponseDto wrapSuccess() {

        return new ResponseDto(BaseResponseEnum.SUCCESS);
    }

    /**
     * Wrap response body of success.
     * @param body returned object
     * @return ResponseDto
     */
    public static ResponseDto wrapSuccess(final Object body) {

        return new ResponseDto(BaseResponseEnum.SUCCESS, body);
    }

    /**
     * @param code error code
     * @param message error message
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final String code, final String message) {

        return new ResponseDto(code, message);
    }

    /**
     * @param e ApplicationException
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final ApplicationException e) {

        return new ResponseDto(e);
    }

    /**
     * @param e Exception
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final Exception e) {

        return wrapException(new SystemException(e));
    }

}
