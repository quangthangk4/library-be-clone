package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.application.mapper.AuthorMapper;
import com.library.catalog.application.mapper.CategoryMapper;
import com.library.catalog.application.mapper.PublicationMapper;
import com.library.catalog.application.mapper.PublisherMapper;
import com.library.catalog.application.mapper.TagMapper;
import com.library.shared.dto.PageResponse;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllPublicationsUseCaseImpl implements GetAllPublicationsUseCase {

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
    public PageResponse<PublicationResponse> execute(int page, int size) {
        log.info("Fetching all publications - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Publication> publicationPage = publicationRepository.findAll(pageable);

        // Collect all IDs for batch fetching
        List<Long> publisherIds = publicationPage.getContent().stream()
            .map(p -> p.getPublisherId().getValue())
            .distinct()
            .collect(Collectors.toList());

        List<Long> authorIds = publicationPage.getContent().stream()
            .flatMap(p -> p.getAuthorIds().stream())
            .map(aid -> aid.getValue())
            .distinct()
            .collect(Collectors.toList());

        List<Long> categoryIds = publicationPage.getContent().stream()
            .flatMap(p -> p.getCategoryIds().stream())
            .map(cid -> cid.getValue())
            .distinct()
            .collect(Collectors.toList());

        List<Long> tagIds = publicationPage.getContent().stream()
            .flatMap(p -> p.getTagIds().stream())
            .map(tid -> tid.getValue())
            .distinct()
            .collect(Collectors.toList());

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
            .collect(Collectors.toList());

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

    @Override
    @Transactional(readOnly = true)
    public List<PublicationResponse> execute() {
        List<Publication> publicationList = publicationRepository.findAll();
        Map<Long, Publisher> publisherMap = publisherRepository.findAll().stream()
                .collect(Collectors.toMap(p -> p.getId().getValue(), p -> p));

        Map<Long, Author> authorMap = authorRepository.findAll().stream()
                .collect(Collectors.toMap(a -> a.getId().getValue(), a -> a));

        Map<Long, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getId().getValue(), c -> c));

        Map<Long, Tag> tagMap = tagRepository.findAll().stream()
                .collect(Collectors.toMap(t -> t.getId().getValue(), t -> t));

        // Map to responses
        return publicationList.stream()
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
                .collect(Collectors.toList());
    }
}
