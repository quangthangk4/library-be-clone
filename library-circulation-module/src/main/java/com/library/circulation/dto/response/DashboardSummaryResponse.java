package com.library.circulation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardSummaryResponse {

    private Overview overview;
    private TodayActivity todayActivity;
    private PendingActions pendingActions;
    private FinesResponse fines;

    @Data
    @Builder
    public static class Overview {
        private long totalUsers;
        private long activeUsers;
        private long totalPublications;
        private long totalItems;
        private long availableItems;
    }

    @Data
    @Builder
    public static class TodayActivity {
        private long borrowedToday;
        private long returnedToday;
        private long damagedToday;
        private long overdueCount;
    }

    @Data
    @Builder
    public static class PendingActions {
        private long waitingForPickup;
        private long overdueTransactions;
        private long reservationsPending;
    }

    @Data
    @Builder
    public static class FinesResponse {
        private long totalUnpaid;
        private BigDecimal totalUnpaidAmount;
        private BigDecimal collectedToday;
    }
}