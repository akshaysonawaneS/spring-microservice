package com.learning.authentication_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET = "AkshaySonawane123456789@69AkshaySonawane123456789";
    private final long EXPIRATION_TIME = 1000*60*60;
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());



    public String generateToken(String username){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        System.out.println("Issued at: " + now);
        System.out.println("Expires at: " + expiryDate);

        return Jwts.builder()
                . setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String token){
        return extractClaims(token).getSubject();
    }

    public Boolean validate(String username, UserDetails userDetails, String token) {
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

