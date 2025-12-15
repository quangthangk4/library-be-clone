package com.library.shared.exception;

import com.library.shared.dto.ApiResponseApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponseApp<?>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponseApp.error(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseApp<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseApp.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseApp<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 1. Lấy ra lỗi đầu tiên trong danh sách các lỗi validation (để message ngắn gọn)
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");

        log.error("Validation failed: {}", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseApp.error(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseApp<?>> handleIllegalStateException(IllegalStateException e) {
        log.error("IllegalStateException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponseApp.error(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseApp<?>> handleGenericException(Exception e) {
        log.error("Unexpected exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseApp.error(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred. Please contact support."
                ));
    }
}
