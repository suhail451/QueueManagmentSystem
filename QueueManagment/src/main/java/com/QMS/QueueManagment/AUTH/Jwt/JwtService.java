package com.QMS.QueueManagment.AUTH.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;

    public SecretKey getsecretKey(){

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    }


    public String generateToken(String user){

        String token= Jwts.builder()
                .subject(user)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+100*60*60))
                .signWith(getsecretKey())
                .compact();
        return token;
    }


    public Claims extractAllclaim(String token){

        return Jwts.parser()
                .verifyWith(getsecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String extractUserName(String token){
        
        return extractAllclaim(token).getSubject();
        

    }
    public Date extractExpiry(String token){
        return extractAllclaim(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        try{
            return extractExpiry(token).before(new Date());

        }
        catch (ExpiredJwtException e){
            return true;

        } catch (Exception e) {
            return true;
        }
    }


    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


   }

