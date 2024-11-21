package com.uzay.securitygeneltekrarr.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final String SECRET_KEY ="BaheEJjdsbnECYD9lUrklgCjXqQzbUnS2WPanM4Wm5eyacBwz2s9lVSYY5XbMZFEDRZd6Ms8IbkZ97oT21DBdw==";


    // Authentication ile token oluşturma metodu
    public String generateToken2(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts
                .builder()
                .subject(authentication.getName())
                .signWith(getSignKey(SECRET_KEY))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("roles", roles)
                .compact();
    }


    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .signWith(getSignKey(SECRET_KEY))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("roles", roles) // rolleri liste olarak ekle
                .compact();
    }

    public SecretKey getSignKey(String SECRET_KEY) {

        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;

    }
    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }



    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<?> roles = claims.get("roles", List.class);
        return roles.stream()
                .filter(role -> role instanceof String)
                .map(role -> (String) role)
                .collect(Collectors.toList());
    }



    public Date getExpirationDateFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Boolean isExpiredToken(String token) {
        if (getExpirationDateFromToken(token).before(new Date())) {
            return true;
        }
        return false;
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isExpiredToken(token);
    }












}
