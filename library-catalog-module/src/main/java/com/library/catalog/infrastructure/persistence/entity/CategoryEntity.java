package com.library.catalog.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_name", columnList = "categoryName", unique = true),
    @Index(name = "idx_parent_category", columnList = "parentCategoryId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String categoryName;

    private Long parentCategoryId; // Self-reference for hierarchy
}
