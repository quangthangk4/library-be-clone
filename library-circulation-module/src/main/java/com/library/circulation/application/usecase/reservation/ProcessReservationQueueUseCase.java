package com.library.circulation.application.usecase.reservation;

/**
 * Use case for processing reservation queue when an item becomes available
 */
public interface ProcessReservationQueueUseCase {

    /**
     * Execute the use case to process the reservation queue for a publication
     * This is called when an item is returned and there are pending reservations
     *
     * @param publicationId the publication ID
     */
    void execute(Long publicationId);
}
