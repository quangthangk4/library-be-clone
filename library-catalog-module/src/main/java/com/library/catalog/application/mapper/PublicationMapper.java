package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.request.CreatePublicationRequest;
import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.valueobject.PublicationMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "subtitle", source = "request.subtitle")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "language", source = "request.language")
    @Mapping(target = "numberOfPages", source = "request.numberOfPages")
    @Mapping(target = "publicationYear", source = "request.publicationYear")
    @Mapping(target = "edition", source = "request.edition")
    @Mapping(target = "coverImageUrl", source = "request.coverImageUrl")
    @Mapping(target = "aiSummary", ignore = true)
    @Mapping(target = "aiTargetAudience", ignore = true)
    @Mapping(target = "fileUrl", ignore = true)
    PublicationMetadata toMetadata(CreatePublicationRequest request);

    /**
     * Convert Publication to PublicationResponse with enriched data.
     *
     * @param publication the publication entity
     * @param publisher the publisher entity
     * @param authors list of authors
     * @param categories list of categories
     * @param tags list of tags
     * @param totalItems total number of items
     * @param availableItems number of available items
     * @param authorMapper mapper for authors
     * @param publisherMapper mapper for publisher
     * @param categoryMapper mapper for categories
     * @param tagMapper mapper for tags
     * @return enriched publication response
     */
    default PublicationResponse toResponse(
            Publication publication,
            Publisher publisher,
            List<Author> authors,
            List<Category> categories,
            List<Tag> tags,
            long totalItems,
            long availableItems,
            AuthorMapper authorMapper,
            PublisherMapper publisherMapper,
            CategoryMapper categoryMapper,
            TagMapper tagMapper) {

        if (publication == null) {
            return null;
        }

        PublisherResponse publisherResponse = publisherMapper.toResponse(publisher);
        List<AuthorResponse> authorResponses = authors.stream()
            .map(authorMapper::toResponse)
            .collect(Collectors.toList());
        List<CategoryResponse> categoryResponses = categories.stream()
            .map(categoryMapper::toResponse)
            .collect(Collectors.toList());
        List<TagResponse> tagResponses = tags.stream()
            .map(tagMapper::toResponse)
            .collect(Collectors.toList());

        return new PublicationResponse(
            publication.getId().getValue(),
            publication.getIsbn() != null ? publication.getIsbn().getValue() : null,
            publication.getMetadata().getTitle(),
            publication.getMetadata().getSubtitle(),
            publication.getMetadata().getDescription(),
            publication.getMetadata().getLanguage(),
            publication.getMetadata().getNumberOfPages(),
            publisherResponse,
            authorResponses,
            publication.getMetadata().getPublicationYear(),
            publication.getMetadata().getEdition(),
            publication.getMetadata().getCoverImageUrl(),
            publication.getSize(),
            publication.getWeight(),
            categoryResponses,
            tagResponses,
            totalItems,
            availableItems,
            null, // createdAt - managed by JPA
            null  // updatedAt - managed by JPA
        );
    }
}
