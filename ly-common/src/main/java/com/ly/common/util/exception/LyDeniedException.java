package com.ly.common.util.exception;

/**
 * @Author liyang
 * @Create 2018-4-29
 * 403 授权拒绝
 */
public class LyDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LyDeniedException() {
    }

    public LyDeniedException(String message) {
        super(message);
    }

    public LyDeniedException(Throwable cause) {
        super(cause);
    }

    public LyDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LyDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
