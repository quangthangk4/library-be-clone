package com.library.circulation.infrastructure.persistence.mapper;

import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.circulation.infrastructure.persistence.entity.FineEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between FineEntity and Fine domain model.
 */
@Component
public class FineEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public FineEntity toEntity(Fine fine) {
        if (fine == null) {
            return null;
        }

        FineEntity entity = FineEntity.builder()
            .transactionId(fine.getTransactionId().getValue())
            .fineAmount(fine.getFineAmount())
            .fineDate(fine.getFineDate())
            .paymentStatus(fine.getPaymentStatus())
            .paidDate(fine.getPaidDate())
            .violationType(fine.getViolationType())
            .build();

        if (fine.getId() != null) {
            entity.setId(fine.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public Fine toDomainModel(FineEntity entity) {
        if (entity == null) {
            return null;
        }

        FineId id = FineId.of(entity.getId());
        TransactionId transactionId = TransactionId.of(entity.getTransactionId());

        return Fine.createForMapper(
            id,
            transactionId,
            entity.getFineAmount(),
            entity.getFineDate(),
            entity.getPaymentStatus(),
            entity.getPaidDate(),
            entity.getViolationType()
        );
    }
}
