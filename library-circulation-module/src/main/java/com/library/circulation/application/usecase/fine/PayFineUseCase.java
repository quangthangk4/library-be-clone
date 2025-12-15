package com.library.circulation.application.usecase.fine;

/**
 * Use case for paying a fine
 */
public interface PayFineUseCase {

    /**
     * Execute the use case to pay a fine
     *
     * @param fineId the fine ID
     */
    void execute(Long fineId);
}
