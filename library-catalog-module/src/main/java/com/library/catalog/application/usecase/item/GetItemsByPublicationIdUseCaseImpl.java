package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.application.mapper.ItemMapper;
import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of GetItemsByPublicationIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetItemsByPublicationIdUseCaseImpl implements GetItemsByPublicationIdUseCase {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> execute(Long publicationId) {
        log.info("Fetching items for publication ID: {}", publicationId);

        // Validate publication exists
        PublicationId pubId = PublicationId.of(publicationId);
        Publication publication = publicationRepository.findById(pubId)
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        // Get items by publication ID
        List<Item> items = itemRepository.findByPublicationId(pubId);

        if (items.isEmpty()) {
            log.info("No items found for publication ID: {}", publicationId);
            return List.of();
        }

        // Build responses with publication title
        String publicationTitle = publication.getMetadata().getTitle();
        List<ItemResponse> responses = items.stream()
            .map(item -> buildItemResponse(item, publicationTitle))
            .collect(Collectors.toList());

        log.info("Found {} items for publication ID: {}", responses.size(), publicationId);
        return responses;
    }

    private ItemResponse buildItemResponse(Item item, String publicationTitle) {
        ItemResponse response = itemMapper.toResponse(item);
        return new ItemResponse(
            response.id(),
            response.publicationId(),
            publicationTitle,
            response.barcode(),
            response.status(),
            response.itemType(),
            item.getBranch(),
            item.getShelf(),
            item.getCondition() != null ? item.getCondition().name() : null,
            item.getAcquiredDate(),
            null, // createdAt - will be populated from entity
            null  // updatedAt - will be populated from entity
        );
    }
}
