package com.library.catalog.infrastructure.persistence.entity;

import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.enums.ConditionItemEnum;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

  @Column(name = "location", length = 100)
  private String location;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ConditionItemEnum condition;

  public ItemEntity() {
  }
}
