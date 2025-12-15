package com.library.circulation.application.usecase.reservation;

import com.library.circulation.application.dto.response.ReservationResponse;

import java.util.List;

/**
 * Use case for retrieving reservations by user ID
 */
public interface GetReservationsByUserIdUseCase {

    /**
     * Execute the use case to get reservations by user ID
     *
     * @param userId the user ID
     * @return list of reservation responses
     */
    List<ReservationResponse> execute(Long userId);
}
