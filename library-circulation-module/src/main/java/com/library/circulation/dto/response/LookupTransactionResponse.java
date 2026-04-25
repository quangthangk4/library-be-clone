package com.library.circulation.dto.response;

import com.library.circulation.domain.enums.TransactionStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LookupTransactionResponse {
    private Long transactionId;
    private Long userId;
    private String studentId;
    private String fullName;
    private Long itemId;
    private String barcode;
    private Long publicationId;
    private String publicationTitle;
    private String branch;
    private String shelf;
    private Instant pickedUpDeadline;
    private TransactionStatus status;
}
