package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.valueobject.*;
import com.library.catalog.infrastructure.persistence.entity.PublicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicationEntityMapper {

    public PublicationEntity toEntity(Publication publication) {
        PublicationEntity entity = new PublicationEntity();
        entity.setId(publication.getId().getValue());
        entity.setIsbn(publication.getIsbn() != null ? publication.getIsbn().getValue() : null);

        // Map metadata
        PublicationMetadata metadata = publication.getMetadata();
        entity.setTitle(metadata.getTitle());
        entity.setSubtitle(metadata.getSubtitle());
        entity.setDescription(metadata.getDescription());
        entity.setLanguage(metadata.getLanguage());
        entity.setNumberOfPages(metadata.getNumberOfPages());
        entity.setAiSummary(metadata.getAiSummary());
        entity.setAiTargetAudience(metadata.getAiTargetAudience());
        entity.setFileUrl(metadata.getFileUrl());
        entity.setPublicationYear(metadata.getPublicationYear());
        entity.setEdition(metadata.getEdition());
        entity.setCoverImageUrl(metadata.getCoverImageUrl());

        entity.setPublisherId(publication.getPublisherId().getValue());
        entity.setSize(publication.getSize());
        entity.setWeight(publication.getWeight());

        // Map relationships - clear first to avoid duplicates
        entity.clearAuthors();
        entity.clearCategories();
        entity.clearTags();

        // Add new relationships
        publication.getAuthorIds().forEach(authorId ->
            entity.addAuthor(authorId.getValue()));

        publication.getCategoryIds().forEach(categoryId ->
            entity.addCategory(categoryId.getValue()));

        publication.getTagIds().forEach(tagId ->
            entity.addTag(tagId.getValue()));

        return entity;
    }

    public Publication toDomainModel(PublicationEntity entity) {
        // Extract IDs from junction entities
        Set<AuthorId> authorIds = entity.getPublicationAuthors().stream()
            .map(pa -> AuthorId.of(pa.getAuthorId()))
            .collect(Collectors.toSet());

        Set<CategoryId> categoryIds = entity.getPublicationCategories().stream()
            .map(pc -> CategoryId.of(pc.getCategoryId()))
            .collect(Collectors.toSet());

        Set<TagId> tagIds = entity.getPublicationTags().stream()
            .map(pt -> TagId.of(pt.getTagId()))
            .collect(Collectors.toSet());

        // Create metadata value object
        PublicationMetadata metadata = PublicationMetadata.builder()
            .title(entity.getTitle())
            .subtitle(entity.getSubtitle())
            .description(entity.getDescription())
            .language(entity.getLanguage())
            .numberOfPages(entity.getNumberOfPages())
            .aiSummary(entity.getAiSummary())
            .aiTargetAudience(entity.getAiTargetAudience())
            .fileUrl(entity.getFileUrl())
            .publicationYear(entity.getPublicationYear())
            .edition(entity.getEdition())
            .coverImageUrl(entity.getCoverImageUrl())
            .build();

        return Publication.createForMapper(
            PublicationId.of(entity.getId()),
            entity.getIsbn() != null ? ISBN.of(entity.getIsbn()) : null,
            metadata,
            PublisherId.of(entity.getPublisherId()),
            entity.getSize(),
            entity.getWeight(),
            authorIds,
            categoryIds,
            tagIds
        );
    }
}
