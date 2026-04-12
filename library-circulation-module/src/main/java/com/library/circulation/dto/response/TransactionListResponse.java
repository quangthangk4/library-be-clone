package com.library.circulation.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.library.circulation.domain.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class TransactionListResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long transactionId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private String fullName;
    private String studentId;
    private BigDecimal fineAmount;
    private Instant borrowedDate;
    private LocalDate dueDate;
    private Instant returnedDate;
    private TransactionStatus status;
}
