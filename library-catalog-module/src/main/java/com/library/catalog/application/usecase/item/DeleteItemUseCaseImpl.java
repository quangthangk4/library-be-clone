package com.library.catalog.application.usecase.item;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of DeleteItemUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteItemUseCaseImpl implements DeleteItemUseCase {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting item with ID: {}", id);

        // Find item to ensure it exists
        Item item = itemRepository.findById(ItemId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        // Delete item
        itemRepository.delete(item);

        log.info("Item deleted successfully with ID: {}", id);
    }
}
