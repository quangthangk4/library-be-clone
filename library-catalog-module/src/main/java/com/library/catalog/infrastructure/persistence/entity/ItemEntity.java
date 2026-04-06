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
    @Index(name = "idx_item_barcode", columnList = "barcode", unique = true),
    @Index(name = "idx_item_publication_id", columnList = "publicationId"),
    @Index(name = "idx_item_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String barcode;

    private Long publicationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemType itemType;

    @Column(length = 100)
    private String branch;

    @Column(length = 100)
    private String shelf;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ConditionItemEnum condition;

    @Column(nullable = false)
    private LocalDate acquiredDate;
}
