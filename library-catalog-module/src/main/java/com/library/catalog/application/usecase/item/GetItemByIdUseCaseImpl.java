package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.application.dto.response.ItemWithPublicationResponse;
import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.application.mapper.ItemMapper;
import com.library.catalog.application.usecase.publication.GetPublicationByIdUseCase;
import com.library.catalog.domain.entities.Item;
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
 * Implementation of GetItemByIdUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetItemByIdUseCaseImpl implements GetItemByIdUseCase {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;
    private final ItemMapper itemMapper;
    private final GetPublicationByIdUseCase getPublicationByIdUseCase;

    @Override
    @Transactional(readOnly = true)
    public ItemWithPublicationResponse execute(Long id) {
        log.info("Fetching item with ID: {}", id);

        // Find item
        Item item = itemRepository.findById(ItemId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        PublicationResponse publicationResponse = getPublicationByIdUseCase.execute(item.getPublicationId().getValue());

        // Build response with publication title
        return buildItemWithPublicationResponse(item, publicationResponse);
    }

    private ItemWithPublicationResponse buildItemWithPublicationResponse(Item item, PublicationResponse publicationResponse) {
        return new ItemWithPublicationResponse(
            item.getId().getValue(),
            publicationResponse,
            item.getBarcode().getValue(),
            item.getStatus().toString(),
            item.getItemType().toString(),
            item.getBranch(),
            item.getShelf(),
            item.getCondition() != null ? item.getCondition().name() : null,
            item.getAcquiredDate(),
            null, // createdAt - will be populated from entity
            null  // updatedAt - will be populated from entity
        );
    }
}
