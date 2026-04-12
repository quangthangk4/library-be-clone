package com.library.catalog.dto.response.item;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.enums.ConditionItemEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDetailResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String barcode;
    private String branch;
    private String shelf;
    private ItemStatus status;
    private ConditionItemEnum condition;
    private String publicationTitle;
}
