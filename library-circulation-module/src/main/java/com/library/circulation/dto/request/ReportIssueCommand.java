package com.library.circulation.dto.request;

import com.library.user.domain.enums.ViolationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ReportIssueCommand(
    @NotNull(message = "type is required")
    ViolationType type,

    @NotNull(message = "fineAmount is required")
    @DecimalMin(value = "0", inclusive = false, message = "fineAmount must be positive")
    BigDecimal fineAmount
) {}
