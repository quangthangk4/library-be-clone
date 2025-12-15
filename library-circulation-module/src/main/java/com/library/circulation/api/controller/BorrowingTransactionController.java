package com.library.circulation.api.controller;

import com.library.circulation.application.dto.request.CreateBorrowingTransactionRequest;
import com.library.circulation.application.dto.request.ReturnItemRequest;
import com.library.circulation.application.dto.response.BorrowingTransactionResponse;
import com.library.circulation.application.usecase.borrowing.*;
import com.library.shared.dto.ApiResponseApp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Borrowing Transaction management.
 * Handles borrowing, returning, and renewing items.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

    private final CreateBorrowingTransactionUseCase createBorrowingTransactionUseCase;
    private final GetTransactionByIdUseCase getTransactionByIdUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;
    private final GetTransactionsByUserIdUseCase getTransactionsByUserIdUseCase;
    private final ReturnItemUseCase returnItemUseCase;
    private final RenewTransactionUseCase renewTransactionUseCase;
    private final GetOverdueTransactionsUseCase getOverdueTransactionsUseCase;

    /**
     * Create a new borrowing transaction (borrow a book).
     * POST /api/v1/transactions
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<BorrowingTransactionResponse> createTransaction(
            @Valid @RequestBody CreateBorrowingTransactionRequest request) {
        log.info("REST request to create borrowing transaction: userId={}, itemId={}",
            request.userId(), request.itemId());
        BorrowingTransactionResponse response = createBorrowingTransactionUseCase.execute(request);
        return ApiResponseApp.created("Borrowing transaction created successfully", response);
    }

    /**
     * Get transaction by ID.
     * GET /api/v1/transactions/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<BorrowingTransactionResponse> getTransactionById(@PathVariable("id") Long id) {
        log.info("REST request to get transaction by ID: {}", id);
        BorrowingTransactionResponse response = getTransactionByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all transactions.
     * GET /api/v1/transactions
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<BorrowingTransactionResponse>> getAllTransactions() {
        log.info("REST request to get all transactions");
        List<BorrowingTransactionResponse> response = getAllTransactionsUseCase.execute();
        return ApiResponseApp.success(response);
    }

    /**
     * Get transactions by user ID.
     * GET /api/v1/transactions/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<BorrowingTransactionResponse>> getTransactionsByUserId(
            @PathVariable("userId") Long userId) {
        log.info("REST request to get transactions for user: {}", userId);
        List<BorrowingTransactionResponse> response = getTransactionsByUserIdUseCase.execute(userId);
        return ApiResponseApp.success(response);
    }

    /**
     * Return a borrowed item.
     * PUT /api/v1/transactions/{id}/return
     */
    @PutMapping("/{id}/return")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<BorrowingTransactionResponse> returnItem(
            @PathVariable("id") Long id,
            @RequestBody(required = false) ReturnItemRequest request) {
        log.info("REST request to return item for transaction: {}", id);

        // Build request with transaction ID
        ReturnItemRequest fullRequest = new ReturnItemRequest(
            id,
            request != null ? request.librarianIdReturn() : null
        );

        BorrowingTransactionResponse response = returnItemUseCase.execute(fullRequest);
        return ApiResponseApp.success("Item returned successfully", response);
    }

    /**
     * Renew a transaction.
     * PUT /api/v1/transactions/{id}/renew
     */
    @PutMapping("/{id}/renew")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<BorrowingTransactionResponse> renewTransaction(@PathVariable("id") Long id) {
        log.info("REST request to renew transaction: {}", id);
        BorrowingTransactionResponse response = renewTransactionUseCase.execute(id);
        return ApiResponseApp.success("Transaction renewed successfully", response);
    }

    /**
     * Get all overdue transactions.
     * GET /api/v1/transactions/overdue
     */
    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<BorrowingTransactionResponse>> getOverdueTransactions() {
        log.info("REST request to get overdue transactions");
        List<BorrowingTransactionResponse> response = getOverdueTransactionsUseCase.execute();
        return ApiResponseApp.success(response);
    }
}
