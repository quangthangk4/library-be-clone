package com.library.circulation.application.usecase.fine;

import com.library.circulation.application.dto.response.FineResponse;

import java.util.List;

/**
 * Use case for getting all fines for a user
 */
public interface GetFinesByUserIdUseCase {

    /**
     * Execute the use case to get all fines for a user
     *
     * @param userId the user ID
     * @return list of fine responses with enriched data
     */
    List<FineResponse> execute(Long userId);
}
