package com.laozhang.maxweb.base;

import java.util.Date;


public class ResponseDto implements IResponse {

    private String code;

    private String message;

    private Date time = new Date();

    private Object body;

    /**
     * default constructor
     */
    public ResponseDto() {

    }

    /**
     * @param response returned body
     */
    public ResponseDto(final IResponse response) {

        this.code = response.getCode();
        this.message = response.getMessage();
        this.body = response.getBody();
    }

    /**
     * @param response IResponse
     * @param body returned body
     */
    public ResponseDto(final IResponse response, final Object body) {

        this.code = response.getCode();
        this.message = response.getMessage();
        this.body = body;
    }

    /**
     * @param code error code
     * @param message error message
     */
    public ResponseDto(final String code, final String message) {

        this.code = code;
        this.message = message;
    }

    /**
     * @param code error code
     * @param message error message
     * @param body returned body
     */
    public ResponseDto(final String code, final String message, final Object body) {
        super();
        this.code = code;
        this.message = message;
        this.body = body;
    }

    /**
     * @param e ApplicationException
     */
    public ResponseDto(final ApplicationException e) {

        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public String getCode() {

        return code;
    }

    public void setCode(final String code) {

        this.code = code;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    public Date getTime() {

        return time;
    }

    public void setTime(final Date time) {

        this.time = time;
    }

    public Object getBody() {

        return body;
    }

    public void setBody(final Object body) {

        this.body = body;
    }

}
