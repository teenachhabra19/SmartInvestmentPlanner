package com.example.Smart_Investment_Planner.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;
    public JwtService(){
        secretKey=generateSecretKey();
    }
    public String generateSecretKey(){
        try{
            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey=keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key"+ e);
        }
    }
    public String generateToken(String username) {
        Map<String,Object> claims=new HashMap<>();
        Date expiration = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
       final Claims claims=extractAllClaims(token);
       return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            throw new JwtException("Token has expired", e);
        }
        catch (Exception e) {
            throw new JwtException("Invalid token", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return isTokenValid(token,userDetails);
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
       if(token==null || token.trim().isEmpty()){
           return false;
       }
       try{
           final String userName=extractUserName(token);
           return (userName.equals(userDetails.getUsername()) &&!isTokenExpired(token));
       }catch(JwtException e){
           return false;
       }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
