package com.library.circulation.api.controller;

import com.library.circulation.application.dto.request.CreateReservationRequest;
import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.application.usecase.reservation.CreateReservationUseCase;
import com.library.shared.dto.ApiResponseApp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Reservation management.
 * Handles creating and managing publication reservations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;

    /**
     * Create a new reservation.
     * POST /api/v1/reservations
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request) {
        log.info("REST request to create reservation: userId={}, publicationId={}",
            request.userId(), request.publicationId());
        ReservationResponse response = createReservationUseCase.execute(request);
        return ApiResponseApp.created("Reservation created successfully", response);
    }

    /**
     * Get reservation by ID.
     * GET /api/v1/reservations/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> getReservationById(@PathVariable Long id) {
        log.info("REST request to get reservation by ID: {}", id);
        // TODO: Implement GetReservationByIdUseCase
        return ApiResponseApp.success("Reservation retrieval not yet implemented");
    }

    /**
     * Get reservations by user ID.
     * GET /api/v1/reservations/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> getReservationsByUserId(@PathVariable Long userId) {
        log.info("REST request to get reservations for user: {}", userId);
        // TODO: Implement GetReservationsByUserIdUseCase
        return ApiResponseApp.success("User reservations retrieval not yet implemented");
    }

    /**
     * Cancel a reservation.
     * DELETE /api/v1/reservations/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<String> cancelReservation(@PathVariable Long id) {
        log.info("REST request to cancel reservation: {}", id);
        // TODO: Implement CancelReservationUseCase
        return ApiResponseApp.success("Reservation cancellation not yet implemented");
    }
}
