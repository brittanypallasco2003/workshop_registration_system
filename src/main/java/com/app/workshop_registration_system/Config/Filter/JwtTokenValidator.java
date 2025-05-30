package com.app.workshop_registration_system.Config.Filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.workshop_registration_system.Utils.JwtUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String headerContent = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerContent == null || !headerContent.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = headerContent.substring(7);
            DecodedJWT decodedJWT = jwtUtils.verifyToken(jwtToken);

            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authoritiesCollection = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication userAuthenticated = new UsernamePasswordAuthenticationToken(username, null,
                    authoritiesCollection);

            context.setAuthentication(userAuthenticated);

        } catch (JWTVerificationException e) {
    
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            body.put("message", "Token inválido: "+e.getMessage());
            body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) );
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            return;
        }

        filterChain.doFilter(request, response);

    }

}
