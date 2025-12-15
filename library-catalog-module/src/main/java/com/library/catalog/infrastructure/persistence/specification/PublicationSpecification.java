package com.library.catalog.infrastructure.persistence.specification;

import com.library.catalog.application.dto.request.GetAllPublicationForLibrarian;
import com.library.catalog.infrastructure.persistence.entity.ItemEntity;
import com.library.catalog.infrastructure.persistence.entity.PublicationCategoryEntity;
import com.library.catalog.infrastructure.persistence.entity.PublicationEntity;
import com.library.catalog.infrastructure.persistence.entity.PublisherEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PublicationSpecification {

    public static Specification<PublicationEntity> buildSpecification(GetAllPublicationForLibrarian request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Keyword search: LIKE search in title, description, isbn, publisherName
            if (request.keyword() != null && !request.keyword().isBlank()) {
                String keyword = "%" + request.keyword().toLowerCase() + "%";

                // Subquery for publisher name search
                Subquery<Long> publisherSubquery = query.subquery(Long.class);
                Root<PublisherEntity> publisherRoot = publisherSubquery.from(PublisherEntity.class);
                publisherSubquery.select(publisherRoot.get("id"))
                    .where(criteriaBuilder.like(
                        criteriaBuilder.lower(publisherRoot.get("publisherName")),
                        keyword
                    ));

                // Combine all keyword searches with OR
                Predicate keywordPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), keyword),
                    root.get("publisherId").in(publisherSubquery)
                );
                predicates.add(keywordPredicate);
            }

            // Category filter: exact match
            if (request.categoryId() != null) {
                Join<PublicationEntity, PublicationCategoryEntity> categoryJoin =
                    root.join("publicationCategories", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), request.categoryId()));
            }

            // Year filter: exact match
            if (request.year() != null) {
                predicates.add(criteriaBuilder.equal(root.get("publicationYear"), request.year()));
            }

            // Availability filter
            if (request.availability() != null && request.availability() != GetAllPublicationForLibrarian.AvailabilityFilter.ALL) {
                Subquery<Long> itemCountSubquery = query.subquery(Long.class);
                Root<ItemEntity> itemRoot = itemCountSubquery.from(ItemEntity.class);
                itemCountSubquery.select(criteriaBuilder.count(itemRoot.get("id")))
                    .where(criteriaBuilder.equal(itemRoot.get("publicationId"), root.get("id")));

                if (request.availability() == GetAllPublicationForLibrarian.AvailabilityFilter.HAS_ITEMS) {
                    predicates.add(criteriaBuilder.greaterThan(itemCountSubquery, 0L));
                } else if (request.availability() == GetAllPublicationForLibrarian.AvailabilityFilter.NO_ITEMS) {
                    predicates.add(criteriaBuilder.equal(itemCountSubquery, 0L));
                }
            }

            // Ensure distinct results (avoid duplicates from joins)
            if (query != null) {
                query.distinct(true);
            }

            // Combine all predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
