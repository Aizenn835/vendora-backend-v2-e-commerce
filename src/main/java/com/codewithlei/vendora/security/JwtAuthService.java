package com.codewithlei.vendora.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtAuthService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    protected SecretKey getSigningKey(){
        byte[] keys = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keys);
    }
    public String generateToken(String email , String role){
        return Jwts.builder()
                .signWith(getSigningKey())
                .setSubject(email)
                .claim("role" , role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }
    public Claims extractPayload(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token){
        return extractPayload(token).getSubject();
    }

    public Boolean isTokenExpired(String token){
        return extractPayload(token).getExpiration().before(new Date());
    }

    public Boolean isTokenValid(String token , UserDetails userDetails){
        String user = extractUsername(token);
        return (user.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
