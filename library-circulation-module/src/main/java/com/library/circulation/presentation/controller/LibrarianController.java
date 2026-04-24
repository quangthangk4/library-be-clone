package com.library.circulation.presentation.controller;

import com.library.circulation.application.dashboard.DashboardChartsUseCase;
import com.library.circulation.application.dashboard.DashboardSummaryUseCase;
import com.library.circulation.dto.enums.DashboardPeriod;
import com.library.circulation.dto.response.DashboardChartsResponse;
import com.library.circulation.dto.response.DashboardSummaryResponse;
import com.library.circulation.dto.response.RiskyUserResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresRole;
import java.math.BigDecimal;
import java.util.List;
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

  @GetMapping("/dashboard/summary")
  @RequiresRole(RoleConstants.LIBRARIAN)
  public ApiResponseApp<DashboardSummaryResponse> getDashboardSummary() {
    return ApiResponseApp.success(dashboardSummaryUseCase.execute());
  }

  @GetMapping("/dashboard/charts")
  @RequiresRole(RoleConstants.LIBRARIAN)
  public ApiResponseApp<DashboardChartsResponse> getCharts(
      @RequestParam(name = "period") DashboardPeriod period) {
    return ApiResponseApp.success(dashboardChartsUseCase.execute(period));
  }

  @GetMapping("/dashboard/risky-users")
  @RequiresRole(RoleConstants.LIBRARIAN)
  public ApiResponseApp<PageResponse<RiskyUserResponse>> getRiskyUsers(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size,
      @RequestParam(name = "sortBy", defaultValue = "creditScore") String sortBy,
      @RequestParam(name = "sortDir", defaultValue = "ASC") String sortDir) {

    List<RiskyUserResponse> dummyList = List.of(
        RiskyUserResponse.builder()
            .userId(1L).fullName("Nguyễn Văn A").email("a@hcmut.edu.vn")
            .phoneNumber("0901234567").creditScore(20)
            .riskyMetrics(RiskyUserResponse.RiskyMetrics.builder()
                .overdueCount(3).unpaidFineCount(2)
                .totalUnpaidAmount(new BigDecimal("150000")).damagedCount(1)
                .build())
            .build(),
        RiskyUserResponse.builder()
            .userId(2L).fullName("Trần Thị B").email("b@hcmut.edu.vn")
            .phoneNumber("0907654321").creditScore(35)
            .riskyMetrics(RiskyUserResponse.RiskyMetrics.builder()
                .overdueCount(1).unpaidFineCount(3)
                .totalUnpaidAmount(new BigDecimal("300000")).damagedCount(0)
                .build())
            .build()
    );

    PageResponse<RiskyUserResponse> dummy = PageResponse.<RiskyUserResponse>builder()
        .content(dummyList)
        .currentPage(0)
        .pageSize(20)
        .totalElements(2)
        .totalPages(1)
        .isFirst(true)
        .isLast(true)
        .build();

    return ApiResponseApp.success(dummy);
  }

}
