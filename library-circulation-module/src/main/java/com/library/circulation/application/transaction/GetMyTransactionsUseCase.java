package com.library.circulation.application.transaction;

import com.library.circulation.dto.response.UserTransactionResponse;
import com.library.shared.dto.PageResponse;

public interface GetMyTransactionsUseCase {
    PageResponse<UserTransactionResponse> execute(Long userId, int page, int size);
}
