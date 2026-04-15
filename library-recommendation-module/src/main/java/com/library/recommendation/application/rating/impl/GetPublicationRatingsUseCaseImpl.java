package com.library.recommendation.application.rating.impl;

import com.library.recommendation.application.rating.GetPublicationRatingsUseCase;
import com.library.recommendation.dto.response.PublicationRatingResponse;
import com.library.recommendation.infrastructure.persistence.repository.RatingJpaRepository;
import com.library.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPublicationRatingsUseCaseImpl implements GetPublicationRatingsUseCase {

  private final RatingJpaRepository ratingJpaRepository;

  @Override
  public PageResponse<PublicationRatingResponse> execute(Long publicationId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<PublicationRatingResponse> ratings = ratingJpaRepository.findAllByPublicationId(
        publicationId, pageable);
    return PageResponse.from(ratings);
  }
}
