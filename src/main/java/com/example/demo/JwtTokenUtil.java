package com.example.demo;

import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "yourSecretKey";
    private static final long EXPIRATION_TIME = 86400000; 

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        byte[] secretBytes = SECRET_KEY.getBytes();
        String base64Key = Base64.getEncoder().encodeToString(secretBytes);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, base64Key)
                .compact();
    }
}
