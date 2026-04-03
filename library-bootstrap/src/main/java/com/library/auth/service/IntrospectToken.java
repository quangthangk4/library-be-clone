package com.library.auth.service;
import com.library.auth.enums.PurposeToken;
import com.nimbusds.jwt.JWTClaimsSet;

import java.security.interfaces.RSAPublicKey;

public interface IntrospectToken {
    boolean verifyToken(String token, RSAPublicKey publicKey, PurposeToken expectedPurpose);
    JWTClaimsSet parseAndVerifyToken(String token, RSAPublicKey publicKey);
}
