package com.library.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // General errors (1000-1099)
    UNCATEGORIZED_EXCEPTION(1000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(1001, "Invalid request", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1002, "Resource not found", HttpStatus.NOT_FOUND),

    // User errors (1100-1199)
    USER_NOT_FOUND(1100, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1101, "User already exists", HttpStatus.CONFLICT),
    USERNAME_ALREADY_EXISTS(1102, "Username already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS(1103, "Email already exists", HttpStatus.CONFLICT),
    INVALID_USERNAME(1104, "Invalid username format", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1105, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1106, "Invalid password", HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVE(1107, "User account is not active", HttpStatus.FORBIDDEN),
    USER_SUSPENDED(1108, "User account is suspended", HttpStatus.FORBIDDEN),

    // Role errors (1200-1299)
    ROLE_NOT_FOUND(1200, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTS(1201, "Role already exists", HttpStatus.CONFLICT),
    ROLE_NAME_ALREADY_EXISTS(1202, "Role name already exists", HttpStatus.CONFLICT),
    CANNOT_DELETE_ROLE(1203, "Cannot delete role - users are assigned to it", HttpStatus.CONFLICT),
    USER_ALREADY_HAS_ROLE(1204, "User already has this role", HttpStatus.CONFLICT),

    // Permission errors (1300-1399)
    PERMISSION_NOT_FOUND(1300, "Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_ALREADY_EXISTS(1301, "Permission already exists", HttpStatus.CONFLICT),
    PERMISSION_NAME_ALREADY_EXISTS(1302, "Permission name already exists", HttpStatus.CONFLICT),
    INSUFFICIENT_PERMISSIONS(1303, "Insufficient permissions", HttpStatus.FORBIDDEN),

    // Authentication errors (1400-1499)
    UNAUTHENTICATED(1400, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1401, "Unauthorized", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(1402, "Invalid token", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1403, "Token expired", HttpStatus.UNAUTHORIZED),

    // Validation errors (1500-1599)
    VALIDATION_ERROR(1500, "Validation error", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(1501, "Invalid input", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode status;

    ErrorCode(int code, String message, HttpStatusCode status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
