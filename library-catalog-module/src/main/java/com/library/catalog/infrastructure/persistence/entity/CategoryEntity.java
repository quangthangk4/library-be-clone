package com.library.catalog.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_name", columnList = "category_name", unique = true),
    @Index(name = "idx_parent_category", columnList = "parent_category_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(name = "parent_category_id")
    private Long parentCategoryId; // Self-reference for hierarchy
}
