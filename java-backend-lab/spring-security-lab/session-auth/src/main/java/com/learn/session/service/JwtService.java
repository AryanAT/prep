package com.learn.session.service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.learn.session.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    public static final String SECRET = "loremipsum1234124121237892318791asfjlhkfaslkjjk132";

    // Generate a token
    public String generateJWT(UserModel userModel, Collection<? extends GrantedAuthority> authorities){
        return Jwts.builder()
        .subject(userModel.getUsername())
        .signWith(getSignedKey())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .issuedAt(new Date(System.currentTimeMillis()))
        .claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
        .compact();
    }

    private SecretKey getSignedKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Parsing verifies the signature and rejects expired tokens.
    public Claims parseJWT(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
