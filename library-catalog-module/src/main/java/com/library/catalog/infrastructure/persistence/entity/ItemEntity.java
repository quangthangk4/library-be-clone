package com.library.catalog.infrastructure.persistence.entity;

import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.entities.ItemType;
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
    @Index(name = "idx_item_publication_id", columnList = "publication_id"),
    @Index(name = "idx_item_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends BaseEntity {

    @Column(name = "publication_id", nullable = false)
    private Long publicationId;

    @Column(nullable = false, unique = true, length = 50)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 20)
    private ItemType itemType;

    @Column(length = 100)
    private String location;

    @Column(name = "acquired_date", nullable = false)
    private LocalDate acquiredDate;
}
