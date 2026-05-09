package com.library.circulation.application.fine;

import com.library.circulation.dto.response.FineResponse;
import com.library.shared.dto.PageResponse;

public interface GetMyFinesUseCase {
    PageResponse<FineResponse> execute(Long userId, String status, int page, int size);
}
