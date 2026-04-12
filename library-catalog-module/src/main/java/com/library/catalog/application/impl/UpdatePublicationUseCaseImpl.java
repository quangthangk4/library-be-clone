package com.library.catalog.application.impl;

import com.library.catalog.application.UpdatePublicationUseCase;
import com.library.catalog.dto.request.publication.UpdatePublicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePublicationUseCaseImpl implements UpdatePublicationUseCase {

    @Override
    public void execute(Long publicationId, UpdatePublicationRequest request) {

    }
}
