package com.bustransport.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    public String findUsername(String token) {
        return exportToken(token).getSubject();
    }

    //JWT den gelen tüm talepleri karşılayacak
    private Claims exportToken(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token).getBody();
    }

    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public boolean tokenControl(String jwt, UserDetails userDetails) {
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !exportToken(jwt).getExpiration().before(new Date()));
    }

    public String generateToken(UserDetails user, Set permission) {

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 24))
                .claim("authorities", permission.toString())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication createAuthenticationFromToken(String token) {
        final Claims claims = exportToken(token);

        String authoritiesString = claims.get("authorities").toString();
        authoritiesString = authoritiesString.replace("[","");
        authoritiesString = authoritiesString.replace("]","");
        authoritiesString = authoritiesString.replace(" ","");

        String[] jwtArray = authoritiesString.split(",");

        List<SimpleGrantedAuthority> authorities = Arrays.stream(jwtArray).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject() /* userId */, "N/A", authorities);
    }
}
