package com.spring.common.exception;

import java.io.Serializable;

/**
 * 业务逻辑错误类-glory
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 600215769988006288L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}