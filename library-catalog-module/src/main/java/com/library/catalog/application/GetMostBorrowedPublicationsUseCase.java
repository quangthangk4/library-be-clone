package com.library.catalog.application;

import com.library.catalog.dto.response.publication.MostBorrowedPublicationsResponse;
import java.util.List;

public interface GetMostBorrowedPublicationsUseCase {

  List<MostBorrowedPublicationsResponse> execute(int limit);
}
