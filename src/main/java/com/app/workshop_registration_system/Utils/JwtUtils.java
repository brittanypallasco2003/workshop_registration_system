package com.app.workshop_registration_system.Utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String secretKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String username;

            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            } else {
                throw new IllegalArgumentException(
                        "Unsupported principal type: " + authentication.getClass().getName());
            }

            String authorities = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.joining(","));

            String token = JWT.create()
                    .withIssuer(userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error al crear el token", exception);
        }

    }

    public DecodedJWT verifyToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(userGenerator).build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(exception.getMessage());
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();

    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        // devuelve un mapa del payload
        return decodedJWT.getClaims();
    }

}
