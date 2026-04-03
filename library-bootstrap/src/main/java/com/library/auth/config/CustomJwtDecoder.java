package com.library.auth.config;

import com.library.auth.enums.PurposeToken;
import com.library.auth.properties.RSAKeyRecord;
import com.library.auth.service.IntrospectToken;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    private final IntrospectToken introspectToken;
    private final NimbusJwtDecoder nimbusJwtDecoder;
    private final RSAKeyRecord rsaKeyRecord;

    @Override
    public Jwt decode(String token) {
        boolean isValid = introspectToken.verifyToken(token, rsaKeyRecord.rsaPublicKey(), PurposeToken.ACCESS);
        if (!isValid) {
            throw new InvalidBearerTokenException("Invalid JWT token", new AppException(ErrorCode.TOKEN_INVALID));
        }
        return nimbusJwtDecoder.decode(token);
    }

}
