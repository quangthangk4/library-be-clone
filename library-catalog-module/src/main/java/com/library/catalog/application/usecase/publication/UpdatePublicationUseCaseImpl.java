//package com.library.catalog.application.usecase.publication;
//
//import com.library.catalog.application.dto.request.UpdatePublicationRequest;
//import com.library.catalog.application.dto.response.PublicationResponse;
//import com.library.catalog.application.mapper.AuthorMapper;
//import com.library.catalog.application.mapper.CategoryMapper;
//import com.library.catalog.application.mapper.PublicationMapper;
//import com.library.catalog.application.mapper.PublisherMapper;
//import com.library.catalog.application.mapper.TagMapper;
//import com.library.catalog.domain.entities.Author;
//import com.library.catalog.domain.entities.Category;
//import com.library.catalog.domain.entities.Publication;
//import com.library.catalog.domain.entities.Publisher;
//import com.library.catalog.domain.entities.Tag;
//import com.library.catalog.domain.repository.AuthorRepository;
//import com.library.catalog.domain.repository.CategoryRepository;
//import com.library.catalog.domain.repository.ItemRepository;
//import com.library.catalog.domain.repository.PublicationRepository;
//import com.library.catalog.domain.repository.PublisherRepository;
//import com.library.catalog.domain.repository.TagRepository;
//import com.library.catalog.domain.valueobject.*;
//import com.library.shared.exception.AppException;
//import com.library.shared.exception.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UpdatePublicationUseCaseImpl implements UpdatePublicationUseCase {
//
//    private final PublicationRepository publicationRepository;
//    private final AuthorRepository authorRepository;
//    private final PublisherRepository publisherRepository;
//    private final CategoryRepository categoryRepository;
//    private final TagRepository tagRepository;
//    private final ItemRepository itemRepository;
//    private final PublicationMapper publicationMapper;
//    private final AuthorMapper authorMapper;
//    private final PublisherMapper publisherMapper;
//    private final CategoryMapper categoryMapper;
//    private final TagMapper tagMapper;
//
//    @Override
//    @Transactional
//    public PublicationResponse execute(Long id, UpdatePublicationRequest request) {
//        log.info("Updating publication with ID: {}", id);
//
//        // Find existing publication
//        Publication publication = publicationRepository.findById(PublicationId.of(id))
//            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));
//
//        // Update metadata if provided
//        if (request.title() != null || request.subtitle() != null ||
//            request.description() != null || request.language() != null ||
//            request.numberOfPages() != null) {
//
//            PublicationMetadata newMetadata = new PublicationMetadata(
//                request.title() != null ? request.title() : publication.getMetadata().getTitle(),
//                request.subtitle() != null ? request.subtitle() : publication.getMetadata().getSubtitle(),
//                request.description() != null ? request.description() : publication.getMetadata().getDescription(),
//                request.language() != null ? request.language() : publication.getMetadata().getLanguage(),
//                request.numberOfPages() != null ? request.numberOfPages() : publication.getMetadata().getNumberOfPages()
//            );
//            publication.updateMetadata(newMetadata);
//        }
//
//        // Update publisher if provided (Publisher is immutable in Publication, needs recreation)
//        Publisher publisher = null;
//        if (request.publisherId() != null && !request.authorIds().isEmpty()) {
//            PublisherId publisherId = PublisherId.of(request.publisherId());
//            publisher = publisherRepository.findById(publisherId)
//                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
//            // Note: Publisher cannot be updated in domain model, it's final
//            // If needed, this would require domain refactoring
//        } else {
//            publisher = publisherRepository.findById(publication.getPublisherId())
//                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
//        }
//
//        // Update authors if provided
//        List<Author> authors;
//        if (request.authorIds() != null && !request.authorIds().isEmpty()) {
//            Set<Long> uniqueIds = new HashSet<>(request.authorIds()); // Giả sử ID là String
//            if (uniqueIds.size() < request.authorIds().size()) {
//                throw new IllegalArgumentException("Danh sách tác giả không được chứa ID trùng lặp.");
//            }
//            List<AuthorId> authorIds = request.authorIds().stream()
//                .map(AuthorId::of)
//                .collect(Collectors.toList());
//            authors = authorRepository.findByIds(authorIds);
//            if (authors.size() != authorIds.size()) {
//                throw new AppException(ErrorCode.AUTHOR_NOT_FOUND);
//            }
//            publication.replaceAuthors(new HashSet<>(authorIds));
//        } else {
//            authors = authorRepository.findByIds(publication.getAuthorIds().stream().toList());
//        }
//
//        // Update categories if provided
//        List<Category> categories;
//        if (request.categoryIds() != null) {
//            if (request.categoryIds().isEmpty()) {
//                publication.clearCategories();
//                categories = List.of();
//            } else {
//                Set<Long> uniqueIds = new HashSet<>(request.categoryIds()); // Giả sử ID là String
//                if (uniqueIds.size() < request.categoryIds().size()) {
//                    throw new IllegalArgumentException("Danh sách category không được trùng lặp.");
//                }
//                List<CategoryId> categoryIds = request.categoryIds().stream()
//                    .map(CategoryId::of)
//                    .collect(Collectors.toList());
//                categories = categoryRepository.findByIds(categoryIds);
//                if (categories.size() != categoryIds.size()) {
//                    throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
//                }
//                publication.replaceCategories(new HashSet<>(categoryIds));
//            }
//        } else {
//            categories = categoryRepository.findByIds(publication.getCategoryIds().stream().toList());
//        }
//
//        // Update tags if provided
//        List<Tag> tags;
//        if (request.tagIds() != null) {
//            if (request.tagIds().isEmpty()) {
//                publication.clearTags();
//                tags = List.of();
//            } else {
//                Set<Long> uniqueIds = new HashSet<>(request.tagIds()); // Giả sử ID là String
//                if (uniqueIds.size() < request.tagIds().size()) {
//                    throw new IllegalArgumentException("Danh sách Tags không được chứa ID trùng lặp.");
//                }
//                List<TagId> tagIds = request.tagIds().stream()
//                    .map(TagId::of)
//                    .collect(Collectors.toList());
//                tags = tagRepository.findByIds(tagIds);
//                if (tags.size() != tagIds.size()) {
//                    throw new AppException(ErrorCode.TAG_NOT_FOUND);
//                }
//                publication.replaceTags(new HashSet<>(tagIds));
//            }
//        } else {
//            tags = tagRepository.findByIds(publication.getTagIds().stream().toList());
//        }
//
//        // Update other fields
//        if (request.publicationYear() != null || request.edition() != null) {
//            publication.updatePublicationInfo(request.publicationYear(), request.edition());
//        }
//        if (request.coverImageUrl() != null) {
//            publication.updateCoverImage(request.coverImageUrl());
//        }
//        if (request.size() != null || request.weight() != null) {
//            publication.updatePhysicalProperties(request.size(), request.weight());
//        }
//
//        // Save updated publication
//        Publication updatedPublication = publicationRepository.save(publication);
//
//        log.info("Publication updated successfully with ID: {}", id);
//
//        // Get item counts
//        long totalItems = itemRepository.countByPublicationId(updatedPublication.getId());
//        long availableItems = itemRepository.countAvailableByPublicationId(updatedPublication.getId());
//
//        // Build response using mapper
//        return publicationMapper.toResponse(
//            updatedPublication,
//            publisher,
//            authors,
//            categories,
//            tags,
//            totalItems,
//            availableItems,
//            authorMapper,
//            publisherMapper,
//            categoryMapper,
//            tagMapper
//        );
//    }
//}
