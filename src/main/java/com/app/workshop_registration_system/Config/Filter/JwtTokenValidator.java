package com.app.workshop_registration_system.Config.Filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.workshop_registration_system.Utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

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
    }

}
