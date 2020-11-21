package com.mikayelovich.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikayelovich.model.dto.UserDto;
import com.mikayelovich.service.AuthService;
import com.mikayelovich.service.UserService;
import com.mikayelovich.util.exception.JwtAuthenticationException;
import com.mikayelovich.util.exception.ServerSideException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Component
public class JwtTokenProvider {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    @Value("${jwt.token.secret}")
    private String secret;

    private Long validityInMilliseconds = 999999999L;

    public JwtTokenProvider(UserService userService, ObjectMapper objectMapper,@Lazy AuthService authService) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long userId) {
        final UserDto authenticatedUserDto = userService.getById(userId);
        String subject;

        try {
            subject = objectMapper.writeValueAsString(authenticatedUserDto);
        } catch (JsonProcessingException e) {
            throw new ServerSideException("Internal Server error");
        }

        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        boolean validateToken = validateToken(token);
        if (validateToken) {
            final UserDto userDto = getSubject(token);
            final Set<GrantedAuthority> authorities = authService.getAuthoritiesFromRoles(userDto.getRoles());
            return new PreAuthenticatedAuthenticationToken(userDto, "", authorities);
        } else throw new JwtAuthenticationException("");
    }

    public UserDto getSubject(String token) {
        final String jsonUserDto = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        try {
            return objectMapper.readValue(jsonUserDto, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new ServerSideException("Internal Server error");
        }

    }

    public Long getUserId(String token) {
        String id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getId();
        return Long.parseLong(id);
    }

    public Optional<String> resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }


}