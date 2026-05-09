package com.library.circulation.infrastructure.service;

import com.library.shared.kafka.KafkaTopics;
import com.library.shared.kafka.event.NotificationMessage;
import com.library.shared.port.ItemStatusPort;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationAssignmentService {

    static final long HOLD_HOURS = 48;

    private static final String FIND_PENDING_SQL = """
        SELECT r.id, r.user_id
        FROM reservations r
        WHERE r.publication_id = :publicationId
          AND r.status = 'PENDING'
          AND (r.preferred_branch = :branch OR r.preferred_branch = 'ANY')
        ORDER BY r.queue_position ASC
        LIMIT 1
        FOR UPDATE OF r SKIP LOCKED
        """;

    private static final String ASSIGN_SQL = """
        UPDATE reservations
        SET status = 'READY_FOR_PICKUP',
            assigned_item_id = :itemId,
            hold_expiration_time = :holdExpTime,
            updated_at = NOW()
        WHERE id = :reservationId
        """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ItemStatusPort itemStatusPort;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Called after an item becomes AVAILABLE.
     * If a matching PENDING reservation exists, assigns the item to it.
     * Returns true if a reservation was assigned.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean tryAssign(long itemId, long publicationId, String branch) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
            FIND_PENDING_SQL, Map.of("publicationId", publicationId, "branch", branch));

        if (rows.isEmpty()) return false;

        Long reservationId = ((Number) rows.get(0).get("id")).longValue();
        Long userId        = ((Number) rows.get(0).get("user_id")).longValue();
        Instant holdExpTime = Instant.now().plus(HOLD_HOURS, ChronoUnit.HOURS);

        jdbcTemplate.update(ASSIGN_SQL, Map.of(
            "itemId", itemId,
            "holdExpTime", holdExpTime,
            "reservationId", reservationId));

        itemStatusPort.updateStatus(itemId, "RESERVED");

        kafkaTemplate.send(KafkaTopics.NOTIFICATION_SEND, new NotificationMessage(
            userId, "BOOK_AVAILABLE",
            "Sách đặt trước đã sẵn sàng",
            String.format("Sách đặt trước của bạn đã có tại thư viện. Hãy đến nhận trong vòng %d giờ.", HOLD_HOURS),
            null, reservationId
        ));

        log.info("Reservation assigned: reservationId={}, itemId={}, userId={}", reservationId, itemId, userId);
        return true;
    }
}
