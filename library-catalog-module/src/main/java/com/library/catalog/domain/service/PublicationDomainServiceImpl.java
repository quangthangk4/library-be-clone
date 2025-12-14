package com.library.catalog.domain.service;

import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.ISBN;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of PublicationDomainService.
 */
@RequiredArgsConstructor
public class PublicationDomainServiceImpl implements PublicationDomainService {

    private final PublicationRepository publicationRepository;
    private final ItemRepository itemRepository;

    @Override
    public void validateUniqueISBN(ISBN isbn) {
        if (isbn != null && publicationRepository.existsByISBN(isbn)) {
            throw new AppException(ErrorCode.ISBN_ALREADY_EXISTS);
        }
    }

    @Override
    public void validateCanDeletePublication(PublicationId id) {
        long itemCount = itemRepository.countByPublicationId(id);
        if (itemCount > 0) {
            throw new AppException(ErrorCode.CANNOT_DELETE_PUBLICATION_HAS_ITEMS);
        }
    }

    @Override
    public boolean isISBNAvailable(ISBN isbn) {
        if (isbn == null) {
            return true;
        }
        return !publicationRepository.existsByISBN(isbn);
    }

    @Override
    public boolean publicationExists(PublicationId id) {
        return publicationRepository.existsById(id);
    }
}
