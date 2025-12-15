package com.library.circulation.application.usecase.fine;

import com.library.circulation.application.dto.response.FineResponse;

/**
 * Use case for getting a fine by ID
 */
public interface GetFineByIdUseCase {

    /**
     * Execute the use case to get a fine by ID
     *
     * @param fineId the fine ID
     * @return the fine response with enriched data
     */
    FineResponse execute(Long fineId);
}
