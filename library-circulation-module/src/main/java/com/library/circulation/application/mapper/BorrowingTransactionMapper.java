package com.library.circulation.application.mapper;

import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.domain.entities.BorrowingTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for BorrowingTransaction.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BorrowingTransactionMapper {

    /**
     * Map BorrowingTransaction to BorrowingTransactionResponse.
     * Note: userFullName, itemBarcode, and publicationTitle must be set manually in use case.
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "itemId", source = "itemId.value")
    @Mapping(target = "librarianIdIssue", source = "librarianIdIssue.value")
    @Mapping(target = "librarianIdReturn", source = "librarianIdReturn.value")
    @Mapping(target = "status", expression = "java(transaction.getStatus().name())")
    @Mapping(target = "daysOverdue", expression = "java(transaction.isOverdue() ? transaction.calculateDaysOverdue() : null)")
    @Mapping(target = "userFullName", ignore = true)
    @Mapping(target = "itemBarcode", ignore = true)
    @Mapping(target = "publicationTitle", ignore = true)
    BorrowingTransactionResponse toResponse(BorrowingTransaction transaction);
}
