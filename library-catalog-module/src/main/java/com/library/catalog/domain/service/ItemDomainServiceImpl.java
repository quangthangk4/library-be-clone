package com.library.catalog.domain.service;

import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of ItemDomainService.
 */
@RequiredArgsConstructor
public class ItemDomainServiceImpl implements ItemDomainService {

    private final ItemRepository itemRepository;
    private final PublicationRepository publicationRepository;

    @Override
    public void validateUniqueBarcode(Barcode barcode) {
        if (itemRepository.existsByBarcode(barcode)) {
            throw new AppException(ErrorCode.BARCODE_ALREADY_EXISTS);
        }
    }

    @Override
    public void validatePublicationExists(PublicationId publicationId) {
        if (!publicationRepository.existsById(publicationId)) {
            throw new AppException(ErrorCode.PUBLICATION_NOT_FOUND);
        }
    }

    @Override
    public boolean isBarcodeAvailable(Barcode barcode) {
        return !itemRepository.existsByBarcode(barcode);
    }
}
