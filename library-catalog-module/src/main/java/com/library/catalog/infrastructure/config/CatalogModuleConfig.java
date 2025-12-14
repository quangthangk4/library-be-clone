package com.library.catalog.infrastructure.config;

import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.service.ItemDomainService;
import com.library.catalog.domain.service.ItemDomainServiceImpl;
import com.library.catalog.domain.service.PublicationDomainService;
import com.library.catalog.domain.service.PublicationDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogModuleConfig {

    @Bean
    public PublicationDomainService publicationDomainService(
            PublicationRepository publicationRepository,
            ItemRepository itemRepository) {
        return new PublicationDomainServiceImpl(publicationRepository, itemRepository);
    }

    @Bean
    public ItemDomainService itemDomainService(
            ItemRepository itemRepository,
            PublicationRepository publicationRepository) {
        return new ItemDomainServiceImpl(itemRepository, publicationRepository);
    }
}
