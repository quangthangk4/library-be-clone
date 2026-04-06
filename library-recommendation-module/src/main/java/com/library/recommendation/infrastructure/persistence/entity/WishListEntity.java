package com.library.recommendation.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for user wish lists.
 */
@Entity
@Table(name = "wish_lists", uniqueConstraints = {
    @UniqueConstraint(name = "uk_wish_list_user_publication", columnNames = {"userId", "publicationId"})
}, indexes = {
    @Index(name = "idx_wish_list_user_id", columnList = "userId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long publicationId;
}
