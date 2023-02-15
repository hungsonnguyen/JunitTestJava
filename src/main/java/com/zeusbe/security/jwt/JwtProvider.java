package com.zeusbe.security.jwt;

import com.zeusbe.security.UserPrinciple.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "swap-now";
    private int jwtExpiration = 60*60*24;
    public String createTokken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrinciple.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid jwt signatture -> meesage",e);
        }catch (MalformedJwtException e ){
            logger.error("Invalid format token -> message",e);
        }catch (ExpiredJwtException e){
            logger.error("Exprired Jwt token -> message",e);
        }catch (UnsupportedJwtException e){
            logger.error("un supported jwt token->message",e);
        }catch (IllegalArgumentException e){
            logger.error("jwt claims string is empty->message ",e);
        }
        return false;
    }
    public String getUserNameFromToken(String token){
        String username = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
        return username;
    }
}
