package com.library.circulation.application.usecase.reservation;

import com.library.circulation.application.dto.request.CreateReservationRequest;
import com.library.circulation.application.dto.response.ReservationResponse;

public interface CreateReservationUseCase {
    ReservationResponse execute(CreateReservationRequest request);
}
