package com.library.catalog.dto.request.item;

import com.library.catalog.domain.enums.ConditionItemEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateItemRequest {
    private Long publicationId;
    private String barcode;
    private String branch;
    private String shelf;
    private ConditionItemEnum condition;
}
