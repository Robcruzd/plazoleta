package com.pragma.plazoleta.infrastructue.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY ="2646294A404E635266556A586E3272357538782F413F442A472D4B6150645367";
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Long extractUserId(String token){
        return extractClaims(token, claims->claims.get("id", Long.class));
    }

    public List<SimpleGrantedAuthority> extractUserRole(String token){
        List<Map<String, String>> authorities = extractClaims(token, claims -> claims.get("role", List.class));
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toList());
    }

    public boolean isValidToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Firma del token no válida");
        }
        catch (MalformedJwtException e){
            logger.error("Token inválido");
        }
        catch (ExpiredJwtException e){
            logger.error("Token expirado");
        }
        return false;
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String parseJwt(String token){
        token = token.substring(7);
        return token;
    }
}
