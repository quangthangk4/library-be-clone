package com.library.circulation.application.usecase.reservation;

import com.library.circulation.application.dto.response.ReservationResponse;

/**
 * Use case for retrieving a reservation by ID
 */
public interface GetReservationByIdUseCase {

    /**
     * Execute the use case to get a reservation by ID
     *
     * @param id the reservation ID
     * @return the reservation response
     */
    ReservationResponse execute(Long id);
}
