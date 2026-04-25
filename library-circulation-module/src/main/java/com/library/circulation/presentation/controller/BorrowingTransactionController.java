package com.library.circulation.presentation.controller;

import com.library.circulation.application.transaction.BorrowRequestUseCase;
import com.library.circulation.application.transaction.ConfirmPickupUseCase;
import com.library.circulation.application.transaction.DirectBorrowUseCase;
import com.library.circulation.application.transaction.GetAllBorrowingTransactionUseCase;
import com.library.circulation.application.transaction.GetAllTransactionByItemUseCase;
import com.library.circulation.application.transaction.LookupForPickupUseCase;
import com.library.circulation.dto.request.BorrowRequestCommand;
import com.library.circulation.dto.request.DirectBorrowCommand;
import com.library.circulation.dto.response.BorrowTransactionResponse;
import com.library.circulation.dto.response.LookupTransactionResponse;
import com.library.circulation.dto.response.TransactionListResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresAuthentication;
import com.library.shared.util.RequiresRole;
import com.library.shared.util.SecurityEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

  private final GetAllBorrowingTransactionUseCase getAllBorrowingTransactionUseCase;
  private final GetAllTransactionByItemUseCase getAllTransactionByItemUseCase;
  private final BorrowRequestUseCase borrowRequestUseCase;
  private final DirectBorrowUseCase directBorrowUseCase;
  private final ConfirmPickupUseCase confirmPickupUseCase;
  private final LookupForPickupUseCase lookupForPickupUseCase;
  private final SecurityEvaluator security;

  @RequiresRole(RoleConstants.LIBRARIAN)
  @GetMapping
  @Operation(summary = "Get all borrowing transactions (librarian)")
  public ApiResponseApp<PageResponse<TransactionListResponse>> getAllTransactions(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ApiResponseApp.success("All transactions",
        getAllBorrowingTransactionUseCase.execute(page, size));
  }

  @RequiresRole(RoleConstants.LIBRARIAN)
  @GetMapping("/items/{id}")
  @Operation(summary = "Get transactions by item (librarian)")
  public ApiResponseApp<PageResponse<TransactionListResponse>> getTransactionsByItem(
      @PathVariable("id") Long itemId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ApiResponseApp.success("All transactions By ItemId",
        getAllTransactionByItemUseCase.execute(itemId, page, size));
  }

  @PostMapping("/borrow")
  @RequiresAuthentication
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Request to borrow an item")
  public ApiResponseApp<BorrowTransactionResponse> borrowItem(
      @Valid @RequestBody BorrowRequestCommand command) {
    Long userId = security.getCurrentUserId();
    return ApiResponseApp.success(borrowRequestUseCase.execute(userId, command));
  }

  @PostMapping("/borrow-direct")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Librarian creates borrow directly at library (studentId + barcode)")
  public ApiResponseApp<BorrowTransactionResponse> borrowDirect(
      @Valid @RequestBody DirectBorrowCommand command) {
    Long librarianId = security.getCurrentUserId();
    return ApiResponseApp.success(directBorrowUseCase.execute(librarianId, command));
  }

  @GetMapping("/lookup")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @Operation(summary = "Lookup a WAITING_FOR_PICKUP transaction (by transactionId OR studentId+barcode)")
  public ApiResponseApp<LookupTransactionResponse> lookupTransaction(
      @RequestParam(name = "transactionId", required = false) Long transactionId,
      @RequestParam(name = "studentId", required = false) String studentId,
      @RequestParam(name = "barcode", required = false) String barcode) {
    return ApiResponseApp.success(lookupForPickupUseCase.execute(transactionId, studentId, barcode));
  }

  @PostMapping("/{id}/confirm-pickup")
  @RequiresRole(RoleConstants.LIBRARIAN)
  @Operation(summary = "Confirm book handover to user (librarian)")
  public ApiResponseApp<BorrowTransactionResponse> confirmPickup(
      @PathVariable("id") Long transactionId) {
    Long librarianId = security.getCurrentUserId();
    return ApiResponseApp.success(confirmPickupUseCase.execute(transactionId, librarianId));
  }
}
