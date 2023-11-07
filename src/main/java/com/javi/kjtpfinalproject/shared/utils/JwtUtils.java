package com.javi.kjtpfinalproject.shared.utils;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
    private JwtUtils() {
    }

    public static String extractJwt(String authHeader) {
        return authHeader.replace("Bearer ", "");
    }

    public static String extractSubjectClaim(String jwtToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getSubject();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
