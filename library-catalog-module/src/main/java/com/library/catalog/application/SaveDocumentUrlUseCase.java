package com.library.catalog.application;

public interface SaveDocumentUrlUseCase {
    String execute(Long publicationId, String s3Key);
}
