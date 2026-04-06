package com.library.catalog.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tags", indexes = {
    @Index(name = "idx_tag_name", columnList = "tagName", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String tagName;
}
