package com.yousset.rentcar.config.security;

import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.services.UserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenProvider {

    @Value("${tokenExpirationMsec:86400000}")
    private Integer tokenExpirationMsec;
    @Value("${tokenSecret:926D96C90030DD58429D2751AC1BDBBC926D96C90030DD58429D2751AC1BDBBC926D96C90030DD58429D2751AC1BDBBC}")
    private String tokenSecret;

    @Autowired
    UserDetails userDetailsImpl;

    public TokenProvider() {
    }

    public String createToken(String user) {
        return createToken(user, null);
    }

    public String createToken(String userName, Users user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec);

        org.springframework.security.core.userdetails.UserDetails principal = userDetailsImpl.getUsersService().loadUserByUsername(userName, user);

        Claims claims = Jwts.claims().setSubject(principal.getUsername());
        claims.put("auth", principal.getAuthorities());

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsImpl.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(tokenSecret).build().parseClaimsJws(token).getBody().getSubject();
    }


    public Claims getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(tokenSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
