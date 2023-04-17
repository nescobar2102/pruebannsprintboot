/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pruebann.services;
  
import com.example.pruebann.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import jdk.internal.HotSpotIntrinsicCandidate;
import org.springframework.beans.factory.annotation.Value;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author norbe
 */
public class JwtUtils {
 private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
 
  @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;
  
    public String generateJwtToken(String email) {     
        System.out.println("genera el token");
      Date expiryDate =new Date(new Date().getTime() + expiration * 1000); 
       
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact(); 
    }
 
    
    public boolean validateTokenEmail(String token, User user) {
        String userEmail = getEmailFromToken(token);
        return userEmail.equals(user.getEmail()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
           logger.error("Token mal formado");
        } catch (UnsupportedJwtException e) {
             logger.error("Token no soportado");
        } catch (ExpiredJwtException e) {
             logger.error("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacio");
        } catch (SignatureException e) {
            logger.error("Fallo con la firma");
        }
        return false;
    }

    public String refreshToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return generateJwtToken(claims.getSubject());    
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    
}
