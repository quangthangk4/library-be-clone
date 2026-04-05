package com.library.circulation.presentation.controller;

import com.library.circulation.application.dto.response.FineResponse;
import com.library.circulation.application.usecase.fine.GetFineByIdUseCase;
import com.library.circulation.application.usecase.fine.GetFinesByUserIdUseCase;
import com.library.circulation.application.usecase.fine.PayFineUseCase;
import com.library.shared.dto.ApiResponseApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Fine management.
 * Handles viewing and paying fines.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
public class FineController {

    private final GetFineByIdUseCase getFineByIdUseCase;
    private final GetFinesByUserIdUseCase getFinesByUserIdUseCase;
    private final PayFineUseCase payFineUseCase;

    /**
     * Get fine by ID.
     * GET /api/v1/fines/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<FineResponse> getFineById(@PathVariable("id") Long id) {
        log.info("REST request to get fine by ID: {}", id);
        FineResponse response = getFineByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get fines by user ID.
     * GET /api/v1/fines/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<FineResponse>> getFinesByUserId(@PathVariable("userId") Long userId) {
        log.info("REST request to get fines for user: {}", userId);
        List<FineResponse> responses = getFinesByUserIdUseCase.execute(userId);
        return ApiResponseApp.success(responses);
    }

    /**
     * Pay a fine.
     * PUT /api/v1/fines/{id}/pay
     */
    @PutMapping("/{id}/pay")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> payFine(@PathVariable("id") Long id) {
        log.info("REST request to pay fine: {}", id);
        payFineUseCase.execute(id);
        return ApiResponseApp.success(null);
    }
}
