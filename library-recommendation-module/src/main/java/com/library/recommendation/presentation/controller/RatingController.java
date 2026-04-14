package com.library.recommendation.presentation.controller;

import com.library.recommendation.dto.response.PublicationRatingResponse;
import com.library.shared.dto.ApiResponseApp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RatingController {


  @GetMapping("/publications/{id}/ratings")
  public ApiResponseApp<PublicationRatingResponse> getPublicationRatings(
      @PathVariable("id") Long id) {
    return null;
  }
}
