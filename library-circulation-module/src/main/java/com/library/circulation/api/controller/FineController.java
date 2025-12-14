package com.library.circulation.api.controller;

import com.library.shared.dto.ApiResponseApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Fine management.
 * Handles viewing and paying fines.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
public class FineController {

    /**
     * Get fine by ID.
     * GET /api/v1/fines/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> getFineById(@PathVariable Long id) {
        log.info("REST request to get fine by ID: {}", id);
        // TODO: Implement GetFineByIdUseCase
        return ApiResponseApp.success("Fine retrieval not yet implemented");
    }

    /**
     * Get fines by user ID.
     * GET /api/v1/fines/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> getFinesByUserId(@PathVariable Long userId) {
        log.info("REST request to get fines for user: {}", userId);
        // TODO: Implement GetFinesByUserIdUseCase
        return ApiResponseApp.success("User fines retrieval not yet implemented");
    }

    /**
     * Pay a fine.
     * PUT /api/v1/fines/{id}/pay
     */
    @PutMapping("/{id}/pay")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> payFine(@PathVariable Long id) {
        log.info("REST request to pay fine: {}", id);
        // TODO: Implement PayFineUseCase
        return ApiResponseApp.success("Fine payment not yet implemented");
    }
}
