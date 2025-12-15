package com.library.circulation.application.usecase.reservation;

/**
 * Use case for canceling a reservation
 */
public interface CancelReservationUseCase {

    /**
     * Execute the use case to cancel a reservation
     *
     * @param reservationId the reservation ID
     */
    void execute(Long reservationId);
}
