package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.request.CreateItemRequest;
import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.application.mapper.ItemMapper;
import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.entities.ItemType;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.service.ItemDomainService;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Implementation of CreateItemUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateItemUseCaseImpl implements CreateItemUseCase {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;
    private final ItemDomainService itemDomainService;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemResponse execute(CreateItemRequest request) {
        log.info("Creating item with barcode: {}", request.barcode());

        // Validate barcode uniqueness
        Barcode barcode = Barcode.of(request.barcode());
        itemDomainService.validateUniqueBarcode(barcode);

        // Validate publication exists
        PublicationId publicationId = PublicationId.of(request.publicationId());
        itemDomainService.validatePublicationExists(publicationId);

        // Get publication for response
        Publication publication = publicationRepository.findById(publicationId)
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        // Parse item type
        ItemType itemType = ItemType.valueOf(request.itemType());

        // Create item entity
        Item item;
        if (request.location() != null && !request.location().isBlank()) {
            item = Item.create(
                publicationId,
                barcode,
                itemType,
                request.location(),
                LocalDate.now()
            );
        } else {
            item = Item.create(
                publicationId,
                barcode,
                itemType,
                LocalDate.now()
            );
        }

        // Save item
        Item savedItem = itemRepository.save(item);

        log.info("Item created successfully with ID: {}", savedItem.getId().getValue());

        // Build response with publication title
        return buildItemResponse(savedItem, publication.getMetadata().getTitle());
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
            response.location(),
            item.getAcquiredDate(),
            null, // createdAt - will be set by entity
            null  // updatedAt - will be set by entity
        );
    }
}
