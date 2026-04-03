package com.library.auth.service.impl;

import com.library.auth.enums.PurposeToken;
import com.library.auth.service.IntrospectToken;
import com.library.shared.util.StaticVariable;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Slf4j
@Service
public class IntrospectTokenImpl implements IntrospectToken {
    @Override
    public boolean verifyToken(String token, RSAPublicKey publicKey, PurposeToken expectedPurpose) {
        try {
            JWTClaimsSet claimsSet = parseAndVerifyToken(token, publicKey);
            // purpose access
            if (!expectedPurpose.name().equals(claimsSet.getClaim(StaticVariable.PURPOSE))) return false;

            if (claimsSet.getExpirationTime().before(new Date())) {
                log.warn("Token has expired");
                return false;
            }
            // Additional checks can be added here, such as audience, issuer, etc.
            return true;
        } catch (Exception e) {
            log.error("Token verification failed", e);
        }
        return false;
    }

    @Override
    public JWTClaimsSet parseAndVerifyToken(String token, RSAPublicKey publicKey) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (!jwsObject.verify(verifier)) {
                log.warn("Invalid token signature");
                return null;
            }
            return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        } catch (Exception e) {
            log.info("get ClaimSet token failed", e);
        }
        return null;
    }
}
