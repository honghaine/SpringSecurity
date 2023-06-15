package com.restAPI.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    // Key secret
    private final String keySecret = "0A94F7DACBAF0EA13679D527D408BA5441E1BCEFEF843CB2B80821C69633038C";
    //Expiration
    private final Long keyExpriration = 600000L;

    /**
     *
     * @param username
     * @return
     */
    public String generateToken(String username) {
//        Generate secret key
        Key key = Keys.hmacShaKeyFor(keySecret.getBytes(StandardCharsets.UTF_8));

        var currentDate = new Date();
        var expiration = new Date(currentDate.getTime() + keyExpriration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
    /**
     *
     * @param token
     * @return
     */
    private Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(keySecret.getBytes())
                .build();
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        }catch (UnsupportedJwtException e) {
            System.out.println(e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println(e.getMessage());
        } catch (SignatureException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        return parseToken(token)!=null;
    }

    /**
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        Claims claims = parseToken(token);

        if(claims!=null) {
            return claims.getSubject();
        }
        return null;
    }


}
