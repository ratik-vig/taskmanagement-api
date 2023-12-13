package com.rjd.taskmananger.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService{

    public String generateToken(UserDetails userDetails, Integer userId, String username){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("userId", userId)
                .claim("username", username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey(){
        byte[] key = Decoders.BASE64.decode("21e00848e423ffe22bde6487b5ab919d4222a86e64666185ceabac9e50dc63d5");
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token){
        System.out.println("check here also" + token);
        return extractClaim(token, Claims::getSubject);
    }

    public Integer extractUserId(String token){
        return extractAllClaims(token).get("userId", Integer.class);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String userEmail = extractUserName(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
