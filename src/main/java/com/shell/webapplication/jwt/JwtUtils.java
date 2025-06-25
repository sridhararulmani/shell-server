package com.shell.webapplication.jwt;

import com.shell.webapplication.auth.dto.CustomUserDetails;
import com.shell.webapplication.exception.customexception.TokenExpirationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    private String accessExpiration;
    @Value("${jwt.refresh.expiration}")
    private String refreshExpiration;

    public String generateToken(CustomUserDetails customUserDetails, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", customUserDetails.getUsername());
        claims.put("userEmail", customUserDetails.getUserEmail());
        claims.put("userRoles", customUserDetails.getAuthorities());
        System.out.println("Generating token for "+customUserDetails.getUsername());
        return Jwts.builder().setClaims(claims).setSubject(customUserDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? TimeUnit.MINUTES.toMillis(Long.parseLong(refreshExpiration))
                                : TimeUnit.MINUTES.toMillis(Long.parseLong(accessExpiration)))))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        return (validateToken(refreshToken) && !isTokenExpired(refreshToken));
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException | TokenExpirationException e) {
            return true;
        }
    }

    public Claims extractAllClaims(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return body;
    }

    public String tokenResolver(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
