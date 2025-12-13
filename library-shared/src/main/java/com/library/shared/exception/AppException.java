package com.library.shared.exception;

import lombok.Getter;

/**
 * Base exception class for all application-specific exceptions.
 * All custom exceptions should extend this class.
 */
@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
