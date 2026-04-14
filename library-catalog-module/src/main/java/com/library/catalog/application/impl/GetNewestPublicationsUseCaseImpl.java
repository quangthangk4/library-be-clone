package com.library.catalog.application.impl;

import com.library.catalog.application.GetNewestPublicationsUseCase;
import com.library.catalog.dto.projection.AuthorNameProjection;
import com.library.catalog.dto.projection.NewestPublicationProjection;
import com.library.catalog.dto.response.publication.NewestPublicationsResponse;
import com.library.catalog.infrastructure.persistence.repository.PublicationJpaRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNewestPublicationsUseCaseImpl implements GetNewestPublicationsUseCase {

  private final PublicationJpaRepository publicationJpaRepository;

  @Override
  public List<NewestPublicationsResponse> execute(int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    List<NewestPublicationProjection> projections = publicationJpaRepository.findNewestPublications(
        pageable);
    // get list publicaiton id
    List<Long> ids = projections.stream().map(NewestPublicationProjection::getPublicationId)
        .toList();

    Map<Long, List<String>> authorsByPubId = publicationJpaRepository
        .findAuthorNamesByPublicationIds(ids)
        .stream()
        .collect(Collectors.groupingBy(
            AuthorNameProjection::getPublicationId,
            Collectors.mapping(AuthorNameProjection::getAuthorName, Collectors.toList())
        ));

    return projections.stream().map(projection ->
        NewestPublicationsResponse.builder()
            .publicationId(projection.getPublicationId())
            .title(projection.getTitle())
            .coverImageUrl(projection.getCoverImageUrl())
            .publicationYear(projection.getPublicationYear())
            .createdAt(projection.getCreatedAt())
            .availableItems(projection.getAvailableItems())
            .authorNames(authorsByPubId.getOrDefault(projection.getPublicationId(), List.of()))
            .ratingAverage(projection.getRatingAverage())
            .ratingCount(projection.getRatingCount())
            .build()).toList();
  }
}
