package com.library.circulation.presentation.controller;

import com.library.circulation.application.transaction.GetAllBorrowingTransactionUseCase;
import com.library.circulation.application.transaction.GetAllTransactionByItemUseCase;
import com.library.circulation.dto.response.TransactionListResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

  private final GetAllBorrowingTransactionUseCase getAllBorrowingTransactionUseCase;
  private final GetAllTransactionByItemUseCase getAllTransactionByItemUseCase;

  @RequiresRole(RoleConstants.LIBRARIAN)
  @GetMapping
  public ApiResponseApp<PageResponse<TransactionListResponse>> getAllTransactions(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ApiResponseApp.success("All transactions",
        getAllBorrowingTransactionUseCase.execute(page, size));
  }

  @RequiresRole(RoleConstants.LIBRARIAN)
  @GetMapping("/items/{id}")
  public ApiResponseApp<PageResponse<TransactionListResponse>> getTransactionsByItem(
      @PathVariable("id") Long itemId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ApiResponseApp.success("All transactions By ItemId",
        getAllTransactionByItemUseCase.execute(itemId, page, size));
  }
}
