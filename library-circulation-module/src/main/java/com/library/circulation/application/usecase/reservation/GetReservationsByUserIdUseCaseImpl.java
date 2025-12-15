package com.library.circulation.application.usecase.reservation;

import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.application.mapper.ReservationMapper;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.repository.ReservationRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of GetReservationsByUserIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetReservationsByUserIdUseCaseImpl implements GetReservationsByUserIdUseCase {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> execute(Long userId) {
        log.info("Getting reservations for user ID: {}", userId);

        // Validate user exists
        User user = userRepository.findById(UserId.of(userId))
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Find all reservations by user
        List<Reservation> reservations = reservationRepository.findByUserId(UserId.of(userId));

        if (reservations.isEmpty()) {
            return List.of();
        }

        // Get all unique publication IDs
        List<PublicationId> publicationIds = reservations.stream()
            .map(Reservation::getPublicationId)
            .distinct()
            .toList();

        // Batch load publications
        Map<Long, Publication> publicationMap = publicationRepository.findByIds(publicationIds).stream()
            .collect(Collectors.toMap(
                p -> p.getId().getValue(),
                p -> p
            ));

        // Map to responses
        List<ReservationResponse> responses = reservations.stream()
            .map(reservation -> {
                Publication publication = publicationMap.get(reservation.getPublicationId().getValue());
                int queuePosition = reservationRepository.getQueuePosition(
                    reservation.getPublicationId(),
                    reservation.getId()
                );
                return reservationMapper.toResponse(reservation, user, publication, queuePosition);
            })
            .collect(Collectors.toList());

        log.info("Successfully retrieved {} reservations for user ID: {}", responses.size(), userId);

        return responses;
    }
}
