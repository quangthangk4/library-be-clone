package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.GetAllPublicationForLibrarian;
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
import com.library.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllPublicationsForLibrarianUseCaseImpl implements GetAllPublicationsForLibrarianUseCase {

    private final PublicationRepository publicationRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ItemRepository itemRepository;
    private final PublicationMapper publicationMapper;
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PublicationResponse> execute(GetAllPublicationForLibrarian request) {
        log.info("Searching publications with criteria: {}", request);

        // Build pageable with sorting
        Pageable pageable = buildPageable(request);

        // Search using Specification pattern
        Page<Publication> publicationPage = publicationRepository.getAllPublicationForLibrarian(request, pageable);

        // Batch fetch related entities
        Map<Long, Publisher> publisherMap = publisherRepository.findAll().stream()
            .collect(Collectors.toMap(p -> p.getId().getValue(), p -> p));

        Map<Long, Author> authorMap = authorRepository.findAll().stream()
            .collect(Collectors.toMap(a -> a.getId().getValue(), a -> a));

        Map<Long, Category> categoryMap = categoryRepository.findAll().stream()
            .collect(Collectors.toMap(c -> c.getId().getValue(), c -> c));

        Map<Long, Tag> tagMap = tagRepository.findAll().stream()
            .collect(Collectors.toMap(t -> t.getId().getValue(), t -> t));

        // Map to responses
        List<PublicationResponse> responses = publicationPage.getContent().stream()
            .map(publication -> {
                Publisher publisher = publisherMap.get(publication.getPublisherId().getValue());
                List<Author> authors = publication.getAuthorIds().stream()
                    .map(aid -> authorMap.get(aid.getValue()))
                    .toList();
                List<Category> categories = publication.getCategoryIds().stream()
                    .map(cid -> categoryMap.get(cid.getValue()))
                    .toList();
                List<Tag> tags = publication.getTagIds().stream()
                    .map(tid -> tagMap.get(tid.getValue()))
                    .toList();

                long totalItems = itemRepository.countByPublicationId(publication.getId());
                long availableItems = itemRepository.countAvailableByPublicationId(publication.getId());

                return publicationMapper.toResponse(
                    publication,
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
            })
            .toList();

        return PageResponse.of(
            responses,
            publicationPage.getNumber(),
            publicationPage.getSize(),
            publicationPage.getTotalElements(),
            publicationPage.getTotalPages(),
            publicationPage.isFirst(),
            publicationPage.isLast()
        );
    }

    private Pageable buildPageable(GetAllPublicationForLibrarian request) {
        if (request.sortBy() != null && !request.sortBy().isBlank()) {
            Sort.Direction direction =
                request.direction() == GetAllPublicationForLibrarian.SortDirection.DESC
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return PageRequest.of(
                request.page(),
                request.size(),
                Sort.by(direction, request.sortBy())
            );
        }
        return PageRequest.of(request.page(), request.size());
    }

}
