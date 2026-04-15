package com.library.catalog.dto.response.item;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.enums.ConditionItemEnum;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ItemsByPublicationIdResponse {

  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
  private String barcode;
  private String branch;
  private String shelf;
  private ItemStatus status;
  private ConditionItemEnum condition;
  private LocalDate dueDate;

  public ItemsByPublicationIdResponse(Long id, String barcode, String branch, String shelf,
      ItemStatus status, ConditionItemEnum condition, LocalDate dueDate) {
    this.id = id;
    this.barcode = barcode;
    this.branch = branch;
    this.shelf = shelf;
    this.status = status;
    this.condition = condition;
    this.dueDate = dueDate;
  }

  public ItemsByPublicationIdResponse(Long id, String barcode, String branch, String shelf,
      ItemStatus status, ConditionItemEnum condition) {
    this.id = id;
    this.barcode = barcode;
    this.branch = branch;
    this.shelf = shelf;
    this.status = status;
    this.condition = condition;
    this.dueDate = null;
  }
}
