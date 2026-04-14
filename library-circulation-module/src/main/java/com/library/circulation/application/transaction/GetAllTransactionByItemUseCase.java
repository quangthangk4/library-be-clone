package com.library.circulation.application.transaction;

import com.library.circulation.dto.response.TransactionListResponse;
import com.library.shared.dto.PageResponse;

public interface GetAllTransactionByItemUseCase {
    PageResponse<TransactionListResponse> execute(Long itemId, int page, int size);
}
