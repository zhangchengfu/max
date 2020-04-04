package com.laozhang.maxweb.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * System exception.
 */
public class SystemException extends ApplicationException {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemException.class);

    private static final long serialVersionUID = -8216248010896000768L;

    /**
     * DEFAULT_SYSTEM_CODE.
     */
    public static final String DEFAULT_SYSTEM_CODE = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();

    /**
     * DEFAULT_SYSTEM_MESSAGE.
     */
    public static final String DEFAULT_SYSTEM_MESSAGE = BaseResponseEnum.SYSTEM_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public SystemException() {
        super();
        this.code = DEFAULT_SYSTEM_CODE;
        this.message = DEFAULT_SYSTEM_MESSAGE;
    }

    /**
     * @param message error message
     */
    public SystemException(final String message) {
        super(DEFAULT_SYSTEM_CODE, message);
        this.code = DEFAULT_SYSTEM_CODE;
        this.message = message;
    }

    /**
     * @param code error code
     * @param message error message
     */
    public SystemException(final String code, final String message) {
        super(code, message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param e Exception
     */
    public SystemException(final Exception e) {
        super(DEFAULT_SYSTEM_CODE, e.getMessage() == null ? DEFAULT_SYSTEM_CODE : e.getMessage());
        this.code = DEFAULT_SYSTEM_CODE;
        final StringBuilder builder = new StringBuilder();
        builder.append(DEFAULT_SYSTEM_MESSAGE);
        if (e.getMessage() != null) {
            builder.append(": ").append(e.getMessage()); //$NON-NLS-1$
        }

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        if (stringWriter != null) {
            builder.append("\r\n").append(stringWriter.toString()); //$NON-NLS-1$
        }
        printWriter.close();
        try {
            stringWriter.close();
        } catch (final IOException e1) {
            LOGGER.error(e.getMessage(), e1);
        }

        this.message = builder.toString();
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
