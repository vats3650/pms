package io.coachbar.pms.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.coachbar.pms.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private final String SECRET_KEY = "deh8qwertyuiopasdfghjklzxcvbnmqwertybr44AsdRgbhbcE45hGYxse";

    public String generateToken(User user) {
        LOGGER.info("Generating new token...");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream()
        .map(Role::name)
        .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        LOGGER.info("Extracting username...");
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<Role> extractRoles(String token) {
        LOGGER.info("Extracting roles...");
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .toList();
    }

    public boolean validateToken(String token) {
        LOGGER.info("Validating token...");
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

}
