package com.library.circulation.application.usecase.reservation;

import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.application.mapper.ReservationMapper;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of GetReservationByIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetReservationByIdUseCaseImpl implements GetReservationByIdUseCase {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse execute(Long id) {
        log.info("Getting reservation by ID: {}", id);

        // Find reservation
        Reservation reservation = reservationRepository.findById(ReservationId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        // Find user
        User user = userRepository.findById(reservation.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Find publication
        Publication publication = publicationRepository.findById(reservation.getPublicationId())
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        // Calculate queue position
        int queuePosition = reservationRepository.getQueuePosition(
            reservation.getPublicationId(),
            reservation.getId()
        );

        log.info("Successfully retrieved reservation with ID: {}", id);

        return reservationMapper.toResponse(reservation, user, publication, queuePosition);
    }
}
