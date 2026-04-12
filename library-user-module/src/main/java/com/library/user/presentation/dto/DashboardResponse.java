package com.library.user.presentation.dto;

import java.time.Instant;
import java.util.List;

public record DashboardResponse(
    SummaryStats summary,
    List<ChartData> chartStats,
    List<RecentActivity> recentActivities
) {
    public record SummaryStats(
        long totalPublications,
        long newPublicationsThisMonth,
        long currentlyBorrowedCopies,
        long overdueLoans,
        long activeUsers,
        double newUsersRate // Tỉ lệ % độc giả mới tăng thêm
    ) {}

    public record ChartData(
        int month,
        long borrows
    ) {}

    public record RecentActivity(
        String userFullName,
        ActivityAction action,
        String targetName,
        Instant timestamp
    ) {}

    public enum ActivityAction {
        BORROW,
        RETURN,
        LOST,
        ADD_BOOK,
        RENEW,
        EXTEND,
        DELETE_BOOK,
        UPDATE_BOOK,
        USER_REGISTER,
        USER_DEACTIVATE
    }
}
