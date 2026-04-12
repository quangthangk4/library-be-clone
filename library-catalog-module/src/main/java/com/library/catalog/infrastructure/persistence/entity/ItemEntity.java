package com.library.catalog.infrastructure.persistence.entity;

import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.entities.ItemType;
import com.library.catalog.domain.enums.ConditionItemEnum;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "items", indexes = {
    @Index(name = "idx_item_publication_id", columnList = "publicationId")
})
@Getter
@Setter
@AllArgsConstructor
public class ItemEntity extends BaseEntity {

    @Column(name = "publication_id", nullable = false)
    private Long publicationId;

    @Column(nullable = false, unique = true, length = 50)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemStatus status;

    @Column(length = 100)
    private String branch;

    @Column(length = 100)
    private String shelf;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ConditionItemEnum condition;

    public ItemEntity() {}
}
