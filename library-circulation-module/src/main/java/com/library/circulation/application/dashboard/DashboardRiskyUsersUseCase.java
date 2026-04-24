package com.library.circulation.application.dashboard;

import com.library.circulation.dto.response.RiskyUserResponse;
import com.library.shared.dto.PageResponse;

public interface DashboardRiskyUsersUseCase {
    PageResponse<RiskyUserResponse> execute(int page, int size, String sortBy, String sortDir);
}
