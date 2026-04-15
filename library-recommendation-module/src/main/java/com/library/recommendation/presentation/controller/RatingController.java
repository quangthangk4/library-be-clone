package com.library.recommendation.presentation.controller;

import com.library.recommendation.application.rating.CreatePublicationRatingUseCase;
import com.library.recommendation.application.rating.GetPublicationRatingSummaryUseCase;
import com.library.recommendation.application.rating.GetPublicationRatingsUseCase;
import com.library.recommendation.dto.request.CreatePublicationRatingRequest;
import com.library.recommendation.dto.response.PublicationRatingResponse;
import com.library.recommendation.dto.response.PublicationRatingSummaryResponse;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresAuthentication;
import com.library.shared.util.SecurityEvaluator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RatingController {


  private final GetPublicationRatingsUseCase getPublicationRatingsUseCase;
  private final CreatePublicationRatingUseCase createPublicationRatingUseCase;
  private final SecurityEvaluator securityEvaluator;
  private final GetPublicationRatingSummaryUseCase getPublicationRatingSummaryUseCase;

  @GetMapping("/publications/{publicationId}/ratings")
  public ApiResponseApp<PageResponse<PublicationRatingResponse>> getPublicationRatings(
      @PathVariable("publicationId") Long publicationId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ApiResponseApp.success(
        "Get publication ratings successful",
        getPublicationRatingsUseCase.execute(publicationId, page, size));
  }


  @PostMapping("/publications/{publicationId}/ratings")
  @RequiresAuthentication
  public ApiResponseApp<Void> createRating(
      @PathVariable("publicationId") Long publicationId,
      @RequestBody @Valid CreatePublicationRatingRequest request) {
    Long userId = securityEvaluator.getCurrentUserId();

    createPublicationRatingUseCase.execute(publicationId, userId, request);
    return ApiResponseApp.success("Create rating successful");
  }

  // public endpoint
  @GetMapping("/publications/{publicationId}/ratings/summary")
  public ApiResponseApp<PublicationRatingSummaryResponse> getPublicationRatingSummary(
      @PathVariable("publicationId") Long publicationId) {
    return ApiResponseApp.success(
        "Get publication rating summary successful",
        getPublicationRatingSummaryUseCase.execute(publicationId));
  }
}
