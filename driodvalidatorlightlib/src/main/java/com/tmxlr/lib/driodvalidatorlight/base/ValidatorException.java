package com.tmxlr.lib.driodvalidatorlight.base;

public class ValidatorException extends Exception {
    private static final long serialVersionUID = 1L;

    public ValidatorException() {
        super();
    }

    public ValidatorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ValidatorException(String detailMessage) {
        super(detailMessage);
    }

    public ValidatorException(Throwable throwable) {
        super(throwable);
    }
}
