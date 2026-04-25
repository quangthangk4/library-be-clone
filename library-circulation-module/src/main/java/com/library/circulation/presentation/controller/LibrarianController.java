package com.library.circulation.presentation.controller;

import com.library.circulation.application.dashboard.DashboardChartsUseCase;
import com.library.circulation.application.dashboard.DashboardRiskyUsersUseCase;
import com.library.circulation.application.dashboard.DashboardSummaryUseCase;
import com.library.circulation.dto.enums.DashboardPeriod;
import com.library.circulation.dto.response.DashboardChartsResponse;
import com.library.circulation.dto.response.DashboardSummaryResponse;
import com.library.circulation.dto.response.RiskyUserResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresRole;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class LibrarianController {

  private final DashboardSummaryUseCase dashboardSummaryUseCase;
  private final DashboardChartsUseCase dashboardChartsUseCase;
  private final DashboardRiskyUsersUseCase dashboardRiskyUsersUseCase;

  @GetMapping("/dashboard/summary")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @Operation(summary = "Get summary data for the dashboard")
  public ApiResponseApp<DashboardSummaryResponse> getDashboardSummary() {
    return ApiResponseApp.success(dashboardSummaryUseCase.execute());
  }

  @GetMapping("/dashboard/charts")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @Operation(summary = "Get charts for the dashboard")
  public ApiResponseApp<DashboardChartsResponse> getCharts(
      @RequestParam(name = "period") DashboardPeriod period) {
    return ApiResponseApp.success(dashboardChartsUseCase.execute(period));
  }

  @GetMapping("/dashboard/risky-users")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @Operation(summary = "Get a list of risky users")
  public ApiResponseApp<PageResponse<RiskyUserResponse>> getRiskyUsers(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      @RequestParam(name = "sortBy", defaultValue = "creditScore") String sortBy,
      @RequestParam(name = "sortDir", defaultValue = "ASC") String sortDir) {
    return ApiResponseApp.success(dashboardRiskyUsersUseCase.execute(page, size, sortBy, sortDir));
  }

}
