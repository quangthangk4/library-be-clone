package com.library.catalog.application.usecase.publication;

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
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPublicationByIdUseCaseImpl implements GetPublicationByIdUseCase {

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
    public PublicationResponse execute(Long id) {
        log.info("Fetching publication with ID: {}", id);

        // Find publication
        Publication publication = publicationRepository.findById(PublicationId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        // Fetch related entities
        Publisher publisher = publisherRepository.findById(publication.getPublisherId())
            .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));

        List<Author> authors = authorRepository.findByIds(publication.getAuthorIds().stream().toList());
        List<Category> categories = categoryRepository.findByIds(publication.getCategoryIds().stream().toList());
        List<Tag> tags = tagRepository.findByIds(publication.getTagIds().stream().toList());

        // Get item counts
        long totalItems = itemRepository.countByPublicationId(publication.getId());
        long availableItems = itemRepository.countAvailableByPublicationId(publication.getId());

        // Build response using mapper
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
    }
}
