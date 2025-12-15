package com.library.circulation.application.usecase.reservation;

import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CancelReservationUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CancelReservationUseCaseImpl implements CancelReservationUseCase {

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public void execute(Long reservationId) {
        log.info("Canceling reservation with ID: {}", reservationId);

        // Find reservation
        Reservation reservation = reservationRepository.findById(ReservationId.of(reservationId))
            .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        // Cancel reservation (domain logic)
        reservation.cancel();

        // Save
        reservationRepository.save(reservation);

        log.info("Successfully canceled reservation with ID: {}", reservationId);
    }
}
