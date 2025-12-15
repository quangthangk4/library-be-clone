package com.library.circulation.application.usecase.reservation;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of ProcessReservationQueueUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessReservationQueueUseCaseImpl implements ProcessReservationQueueUseCase {

    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void execute(Long publicationId) {
        log.info("Processing reservation queue for publication ID: {}", publicationId);

        // Find pending reservations ordered by date (FIFO)
        List<Reservation> pendingReservations = reservationRepository
            .findPendingByPublicationId(PublicationId.of(publicationId));

        if (pendingReservations.isEmpty()) {
            log.info("No pending reservations for publication ID: {}", publicationId);
            return;
        }

        // Get the first reservation in queue
        Reservation firstReservation = pendingReservations.get(0);

        // Find an available item for this publication
        List<Item> availableItems = itemRepository.findAvailableByPublicationId(
            PublicationId.of(publicationId)
        );

        if (availableItems.isEmpty()) {
            log.warn("No available items for publication ID: {}, cannot fulfill reservation", publicationId);
            return;
        }

        // Reserve the first available item
        Item item = availableItems.get(0);
        item.markAsReserved();
        itemRepository.save(item);

        // Fulfill the reservation
        firstReservation.fulfill();
        firstReservation.sendNotification();
        reservationRepository.save(firstReservation);

        log.info("Successfully fulfilled reservation ID: {} for publication ID: {}",
            firstReservation.getId().getValue(), publicationId);

        // TODO: In a real system, send actual notification (email/SMS) to user
        log.info("Notification sent to user ID: {} for reservation ID: {}",
            firstReservation.getUserId().getValue(),
            firstReservation.getId().getValue());
    }
}
