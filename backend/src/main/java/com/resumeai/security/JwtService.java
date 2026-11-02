package com.resumeai.security;
import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service public class JwtService {
 @Value("${app.jwt.secret}") private String secret; @Value("${app.jwt.expiration-ms}") private long expiry;
 private SecretKey key(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String generate(Long userId,String email){return Jwts.builder().subject(email).claim("uid",userId).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiry)).signWith(key()).compact();}
 public String email(String token){return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();}
 public Long userId(String token){return ((Number)Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().get("uid")).longValue();}
}
