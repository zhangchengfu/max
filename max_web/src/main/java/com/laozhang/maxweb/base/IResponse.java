package com.laozhang.maxweb.base;

/**
 * Response interface
 */
public interface IResponse {

    /**
     * @return response code
     */
    String getCode();

    /**
     * @return response message
     */
    String getMessage();

    /**
     * @return response body
     */
    Object getBody();

}
