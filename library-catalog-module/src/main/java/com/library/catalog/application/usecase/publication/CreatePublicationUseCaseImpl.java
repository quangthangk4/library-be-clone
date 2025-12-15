package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.CreatePublicationRequest;
import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.application.mapper.AuthorMapper;
import com.library.catalog.application.mapper.CategoryMapper;
import com.library.catalog.application.mapper.PublicationMapper;
import com.library.catalog.application.mapper.PublisherMapper;
import com.library.catalog.application.mapper.TagMapper;
import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.repository.AuthorRepository;
import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.repository.PublisherRepository;
import com.library.catalog.domain.repository.TagRepository;
import com.library.catalog.domain.service.PublicationDomainService;
import com.library.catalog.domain.valueobject.*;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePublicationUseCaseImpl implements CreatePublicationUseCase {

    private final PublicationRepository publicationRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ItemRepository itemRepository;
    private final PublicationDomainService publicationDomainService;
    private final PublicationMapper publicationMapper;
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public PublicationResponse execute(CreatePublicationRequest request) {
        log.info("Creating publication with title: {}", request.title());

        // Validate ISBN uniqueness if provided
        ISBN isbn = null;
        if (request.isbn() != null && !request.isbn().isBlank()) {
            isbn = ISBN.of(request.isbn());
            publicationDomainService.validateUniqueISBN(isbn);
        }

        // Validate and fetch publisher
        PublisherId publisherId = PublisherId.of(request.publisherId());
        Publisher publisher = publisherRepository.findById(publisherId)
            .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));

        // Validate and collect author IDs
        List<AuthorId> authorIds = request.authorIds().stream()
            .map(AuthorId::of)
            .collect(Collectors.toList());

        List<Author> authors = authorRepository.findByIds(authorIds);
        if (authors.size() != authorIds.size()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_FOUND);
        }

        // Validate and collect category IDs if provided
        Set<CategoryId> categoryIds = Set.of();
        List<Category> categories = List.of();
        if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
            categoryIds = request.categoryIds().stream()
                .map(CategoryId::of)
                .collect(Collectors.toSet());
            categories = categoryRepository.findByIds(categoryIds.stream().toList());
            if (categories.size() != categoryIds.size()) {
                throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
            }
        }

        // Validate and collect tag IDs if provided
        Set<TagId> tagIds = Set.of();
        List<Tag> tags = List.of();
        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            tagIds = request.tagIds().stream()
                .map(TagId::of)
                .collect(Collectors.toSet());
            tags = tagRepository.findByIds(tagIds.stream().toList());
            if (tags.size() != tagIds.size()) {
                throw new AppException(ErrorCode.TAG_NOT_FOUND);
            }
        }

        // Create metadata
        PublicationMetadata metadata = publicationMapper.toMetadata(request);

        // Create publication entity
        Publication publication = Publication.create(
            isbn,
            metadata,
            publisherId,
            authorIds.stream().collect(Collectors.toSet()),
            request.publicationYear()
        );

        // Set optional fields
        if (request.edition() != null && !request.edition().isBlank()) {
            publication.updatePublicationInfo(null, request.edition());
        }
        if (request.coverImageUrl() != null && !request.coverImageUrl().isBlank()) {
            publication.updateCoverImage(request.coverImageUrl());
        }
        if (request.size() != null || request.weight() != null) {
            publication.updatePhysicalProperties(request.size(), request.weight());
        }

        // Assign categories and tags
        categoryIds.forEach(publication::assignCategory);
        tagIds.forEach(publication::assignTag);

        // Save publication
        Publication savedPublication = publicationRepository.save(publication);

        log.info("Publication created successfully with ID: {}", savedPublication.getId().getValue());

        // Get item counts
        long totalItems = itemRepository.countByPublicationId(savedPublication.getId());
        long availableItems = itemRepository.countAvailableByPublicationId(savedPublication.getId());

        // Build enriched response using mapper
        return publicationMapper.toResponse(
            savedPublication,
            publisher,
            authors,
            categories,
            tags,
            totalItems,
            availableItems,
            authorMapper,
            publisherMapper,
            categoryMapper,
            tagMapper
        );
    }
}
