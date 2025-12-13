package com.library.user.api.controller;

import com.library.shared.dto.ApiResponseApp;
import com.library.user.application.dto.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Role management
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    // TODO: Inject Role-related use cases when implemented

    /**
     * Get all roles
     * GET /api/v1/roles
     */
    @GetMapping
    public ApiResponseApp<List<RoleResponse>> getAllRoles() {
        log.info("REST request to get all roles");
        // TODO: Implement GetAllRolesUseCase
        return ApiResponseApp.<List<RoleResponse>>builder()
            .code(HttpStatus.OK.value())
            .message("Roles retrieved successfully")
            .data(List.of())
            .build();
    }

    /**
     * Get role by ID
     * GET /api/v1/roles/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseApp<RoleResponse> getRoleById(@PathVariable Long id) {
        log.info("REST request to get role by ID: {}", id);
        // TODO: Implement GetRoleByIdUseCase
        return ApiResponseApp.<RoleResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Role retrieved successfully")
            .build();
    }

    /**
     * Create a new role
     * POST /api/v1/roles
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<RoleResponse> createRole(@RequestBody Object request) {
        log.info("REST request to create role");
        // TODO: Implement CreateRoleUseCase
        return ApiResponseApp.<RoleResponse>builder()
            .code(HttpStatus.CREATED.value())
            .message("Role created successfully")
            .build();
    }

    /**
     * Add permission to role
     * POST /api/v1/roles/{roleId}/permissions/{permissionId}
     */
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ApiResponseApp<RoleResponse> addPermissionToRole(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {
        log.info("REST request to add permission {} to role {}", permissionId, roleId);
        // TODO: Implement AddPermissionToRoleUseCase
        return ApiResponseApp.<RoleResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Permission added to role successfully")
            .build();
    }

    /**
     * Remove permission from role
     * DELETE /api/v1/roles/{roleId}/permissions/{permissionId}
     */
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ApiResponseApp<RoleResponse> removePermissionFromRole(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {
        log.info("REST request to remove permission {} from role {}", permissionId, roleId);
        // TODO: Implement RemovePermissionFromRoleUseCase
        return ApiResponseApp.<RoleResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Permission removed from role successfully")
            .build();
    }
}
