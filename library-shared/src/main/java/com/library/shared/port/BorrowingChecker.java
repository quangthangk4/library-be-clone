package com.library.shared.port;

public interface BorrowingChecker {

  boolean hasBorrowedPublication(Long userId, Long publicationId);
}
