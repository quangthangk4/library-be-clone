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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of GetAllItemsUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllItemsUseCaseImpl implements GetAllItemsUseCase {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> execute() {
        log.info("Fetching all items");

        // Get all items
        List<Item> items = itemRepository.findAll();

        if (items.isEmpty()) {
            log.info("No items found");
            return List.of();
        }

        // Get unique publication IDs
        List<PublicationId> publicationIds = items.stream()
            .map(Item::getPublicationId)
            .distinct()
            .collect(Collectors.toList());

        // Fetch all publications at once
        List<Publication> publications = publicationRepository.findByIds(publicationIds);

        // Create a map for quick lookup
        Map<Long, String> publicationTitleMap = new HashMap<>();
        for (Publication publication : publications) {
            publicationTitleMap.put(
                publication.getId().getValue(),
                publication.getMetadata().getTitle()
            );
        }

        // Build responses
        List<ItemResponse> responses = items.stream()
            .map(item -> {
                String publicationTitle = publicationTitleMap.get(item.getPublicationId().getValue());
                if (publicationTitle == null) {
                    throw new AppException(ErrorCode.PUBLICATION_NOT_FOUND);
                }
                return buildItemResponse(item, publicationTitle);
            })
            .collect(Collectors.toList());

        log.info("Found {} items", responses.size());
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
            response.location(),
            item.getAcquiredDate(),
            null, // createdAt - will be populated from entity
            null  // updatedAt - will be populated from entity
        );
    }
}
