package com.library.circulation.application.usecase.fine;

import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.valueobject.FineId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of PayFineUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayFineUseCaseImpl implements PayFineUseCase {

    private final FineRepository fineRepository;

    @Override
    @Transactional
    public void execute(Long fineId) {
        log.info("Paying fine with ID: {}", fineId);

        // Find fine
        Fine fine = fineRepository.findById(FineId.of(fineId))
            .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));

        // Mark as paid (domain logic)
        fine.markAsPaid();

        // Save
        fineRepository.save(fine);

        log.info("Successfully paid fine with ID: {}", fineId);
    }
}
