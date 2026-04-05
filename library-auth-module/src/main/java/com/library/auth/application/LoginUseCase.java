package com.library.auth.application;

import com.library.user.application.dto.request.LoginRequest;
import com.library.auth.dto.response.TokenResponse;

public interface LoginUseCase {
    TokenResponse execute(LoginRequest request);
}
