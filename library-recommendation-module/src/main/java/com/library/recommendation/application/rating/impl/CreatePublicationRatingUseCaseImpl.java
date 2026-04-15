package com.library.recommendation.application.rating.impl;

import com.library.recommendation.application.rating.CreatePublicationRatingUseCase;
import com.library.recommendation.dto.request.CreatePublicationRatingRequest;
import com.library.recommendation.infrastructure.persistence.entity.RatingEntity;
import com.library.recommendation.infrastructure.persistence.repository.RatingJpaRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.shared.port.BorrowingChecker;
import com.library.shared.util.TsIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePublicationRatingUseCaseImpl implements CreatePublicationRatingUseCase {

  private final RatingJpaRepository ratingJpaRepository;
  private final BorrowingChecker borrowingChecker;

  @Override
  @Transactional
  public void execute(Long publicationId, Long userId, CreatePublicationRatingRequest request) {
    if (!borrowingChecker.hasBorrowedPublication(userId, publicationId)) {
      throw new AppException(ErrorCode.USER_NOT_BORROWED_PUBLICATION);
    }
    RatingEntity rating = RatingEntity.builder()
        .userId(userId)
        .publicationId(publicationId)
        .comment(request.getComment())
        .star(request.getStar())
        .build();

    rating.setId(TsIdGenerator.next());
    try {
      ratingJpaRepository.save(rating);
    } catch (DataIntegrityViolationException e) {
      throw new AppException(ErrorCode.RATING_ALREADY_EXISTS);
    }
  }
}
