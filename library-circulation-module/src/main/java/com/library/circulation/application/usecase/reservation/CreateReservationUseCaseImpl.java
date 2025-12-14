package com.library.circulation.application.usecase.reservation;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.application.dto.request.CreateReservationRequest;
import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.application.mapper.ReservationMapper;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.circulation.domain.service.CirculationDomainService;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateReservationUseCaseImpl implements CreateReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CirculationDomainService circulationDomainService;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse execute(CreateReservationRequest request) {
        log.info("Creating reservation for user: {}, publication: {}", request.userId(), request.publicationId());

        UserId userId = UserId.of(request.userId());
        PublicationId publicationId = PublicationId.of(request.publicationId());

        // Validate user can borrow
        circulationDomainService.validateUserCanBorrow(userId);

        // Validate reservation doesn't exist
        circulationDomainService.validateReservation(userId, publicationId);

        // Create reservation
        Reservation reservation = Reservation.create(userId, publicationId);

        // Save reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Build response
        ReservationResponse response = reservationMapper.toResponse(savedReservation);

        // Enrich with user data and queue position
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Reservation> pendingReservations = reservationRepository.findPendingByPublicationId(publicationId);
        int queuePosition = pendingReservations.indexOf(savedReservation) + 1;

        response = new ReservationResponse(
            response.id(),
            response.userId(),
            user.getProfile().getFullName(),
            response.publicationId(),
            null, // publicationTitle
            response.reservationDate(),
            response.status(),
            response.notificationSentDate(),
            queuePosition
        );

        log.info("Successfully created reservation with ID: {}", savedReservation.getId().getValue());

        return response;
    }
}
