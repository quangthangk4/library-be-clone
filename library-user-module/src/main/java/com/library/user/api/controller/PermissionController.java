package com.library.user.api.controller;

import com.library.shared.dto.ApiResponseApp;
import com.library.user.application.dto.response.PermissionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Permission management
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    // TODO: Inject Permission-related use cases when implemented

    /**
     * Get all permissions
     * GET /api/v1/permissions
     */
    @GetMapping
    public ApiResponseApp<List<PermissionResponse>> getAllPermissions() {
        log.info("REST request to get all permissions");
        // TODO: Implement GetAllPermissionsUseCase
        return ApiResponseApp.<List<PermissionResponse>>builder()
            .code(HttpStatus.OK.value())
            .message("Permissions retrieved successfully")
            .data(List.of())
            .build();
    }

    /**
     * Get permission by ID
     * GET /api/v1/permissions/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseApp<PermissionResponse> getPermissionById(@PathVariable Long id) {
        log.info("REST request to get permission by ID: {}", id);
        // TODO: Implement GetPermissionByIdUseCase
        return ApiResponseApp.<PermissionResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Permission retrieved successfully")
            .build();
    }

    /**
     * Create a new permission
     * POST /api/v1/permissions
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<PermissionResponse> createPermission(@RequestBody Object request) {
        log.info("REST request to create permission");
        // TODO: Implement CreatePermissionUseCase
        return ApiResponseApp.<PermissionResponse>builder()
            .code(HttpStatus.CREATED.value())
            .message("Permission created successfully")
            .build();
    }
}
