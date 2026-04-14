package com.library.catalog.application;

import com.library.catalog.dto.response.publication.NewestPublicationsResponse;
import java.util.List;

public interface GetNewestPublicationsUseCase {

  List<NewestPublicationsResponse> execute(int limit);
}
