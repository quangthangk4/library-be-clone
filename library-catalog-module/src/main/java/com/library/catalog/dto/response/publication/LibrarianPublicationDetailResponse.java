package com.library.catalog.dto.response.publication;

import com.library.catalog.dto.response.author.AuthorOverviewResponse;
import com.library.catalog.dto.response.category.CategoryOverviewResponse;
import com.library.catalog.dto.response.item.ItemOverviewResponse;
import com.library.catalog.dto.response.publisher.PublisherOverviewResponse;
import com.library.catalog.dto.response.tag.TagResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LibrarianPublicationDetailResponse {
    private PublicationResponse publication;
    private PublisherOverviewResponse publisher;
    private List<AuthorOverviewResponse>  authors;
    private List<TagResponse> tags;
    private List<CategoryOverviewResponse> categories;
    private RatingOverviewResponse ratings;
    private ItemOverviewResponse items;

    @Data
    @Builder
    public static class RatingOverviewResponse {
        private Double averageRating;
        private Integer totalRatings;
    }
}
