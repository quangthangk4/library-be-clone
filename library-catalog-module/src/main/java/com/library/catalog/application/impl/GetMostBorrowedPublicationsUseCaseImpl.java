package com.library.catalog.application.impl;

import com.library.catalog.application.GetMostBorrowedPublicationsUseCase;
import com.library.catalog.dto.projection.AuthorNameProjection;
import com.library.catalog.dto.projection.MostBorrowedPublicationProjection;
import com.library.catalog.dto.response.publication.MostBorrowedPublicationsResponse;
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
public class GetMostBorrowedPublicationsUseCaseImpl implements GetMostBorrowedPublicationsUseCase {

  private final PublicationJpaRepository publicationJpaRepository;

  @Override
  public List<MostBorrowedPublicationsResponse> execute(int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    List<MostBorrowedPublicationProjection> projections = publicationJpaRepository.findMostBorrowedPublications(
        pageable);
    
    List<Long> ids = projections.stream().map(MostBorrowedPublicationProjection::getPublicationId)
        .toList();

    if (ids.isEmpty()) {
      return List.of();
    }

    Map<Long, List<String>> authorsByPubId = publicationJpaRepository
        .findAuthorNamesByPublicationIds(ids)
        .stream()
        .collect(Collectors.groupingBy(
            AuthorNameProjection::getPublicationId,
            Collectors.mapping(AuthorNameProjection::getAuthorName, Collectors.toList())
        ));

    return projections.stream().map(projection ->
        MostBorrowedPublicationsResponse.builder()
            .publicationId(projection.getPublicationId())
            .title(projection.getTitle())
            .coverImageUrl(projection.getCoverImageUrl())
            .publicationYear(projection.getPublicationYear())
            .createdAt(projection.getCreatedAt())
            .availableItems(projection.getAvailableItems())
            .authorNames(authorsByPubId.getOrDefault(projection.getPublicationId(), List.of()))
            .ratingAverage(projection.getRatingAverage() != null ? projection.getRatingAverage().doubleValue() : null)
            .ratingCount(projection.getRatingCount())
            .borrowCount(projection.getBorrowCount())
            .build()).toList();
  }
}
