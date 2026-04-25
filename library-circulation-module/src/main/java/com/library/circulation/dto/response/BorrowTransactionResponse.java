package com.library.circulation.dto.response;

import com.library.circulation.domain.enums.TransactionStatus;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowTransactionResponse {
    private Long transactionId;
    private Long itemId;
    private String barcode;
    private Long publicationId;
    private String publicationTitle;
    private String branch;
    private String shelf;
    private Instant pickedUpDeadline;
    private LocalDate dueDate;
    private TransactionStatus status;
}
