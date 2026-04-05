package com.library.auth.application;


import com.library.auth.dto.response.TokenResponse;

public interface RefreshAccessTokenUseCase {
    TokenResponse execute(String refreshToken);
}
