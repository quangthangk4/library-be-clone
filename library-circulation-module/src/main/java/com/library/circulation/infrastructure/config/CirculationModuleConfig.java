package com.library.circulation.infrastructure.config;

import com.library.catalog.domain.repository.ItemRepository;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.circulation.domain.service.CirculationDomainService;
import com.library.circulation.domain.service.CirculationDomainServiceImpl;
import com.library.user.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Circulation module.
 * Defines beans and module-specific configurations.
 */
@Configuration
public class CirculationModuleConfig {

    /**
     * Bean for CirculationDomainService implementation.
     */
    @Bean
    public CirculationDomainService circulationDomainService(
            UserRepository userRepository,
            ItemRepository itemRepository,
            BorrowingTransactionRepository transactionRepository,
            ReservationRepository reservationRepository,
            FineRepository fineRepository) {
        return new CirculationDomainServiceImpl(
            userRepository,
            itemRepository,
            transactionRepository,
            reservationRepository,
            fineRepository
        );
    }
}
