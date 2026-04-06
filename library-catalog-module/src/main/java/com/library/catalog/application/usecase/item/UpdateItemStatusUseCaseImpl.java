package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.request.UpdateItemStatusRequest;
import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.application.mapper.ItemMapper;
import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UpdateItemStatusUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateItemStatusUseCaseImpl implements UpdateItemStatusUseCase {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemResponse execute(Long id, UpdateItemStatusRequest request) {
        log.info("Updating status for item with ID: {} to {}", id, request.status());

        // Find item
        Item item = itemRepository.findById(ItemId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        // Parse new status
        ItemStatus newStatus = ItemStatus.valueOf(request.status());

        // Update status using domain methods for business rules validation
        switch (newStatus) {
            case AVAILABLE -> item.markAsAvailable();
            case BORROWED -> item.markAsBorrowed();
            case RESERVED -> item.markAsReserved();
            case IN_MAINTENANCE -> item.sendToMaintenance();
            case LOST -> item.markAsLost();
        }

        // Save item
        Item updatedItem = itemRepository.save(item);

        log.info("Item status updated successfully for ID: {} to {}", id, newStatus);

        // Get publication for title
        Publication publication = publicationRepository.findById(item.getPublicationId())
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        // Build response with publication title
        return buildItemResponse(updatedItem, publication.getMetadata().getTitle());
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
