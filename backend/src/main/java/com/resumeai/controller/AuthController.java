package com.resumeai.controller;
import com.resumeai.model.AppUser; import com.resumeai.repository.UserRepository; import com.resumeai.security.JwtService; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 record AuthRequest(String name,String email,String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody AuthRequest r){if(r.email()==null||r.password()==null||r.password().length()<6||r.name()==null||r.name().isBlank())return ResponseEntity.badRequest().body(Map.of("message","Name, valid email and password (6+ characters) are required."));String email=r.email().trim().toLowerCase();if(users.findByEmail(email).isPresent())return ResponseEntity.status(409).body(Map.of("message","An account with this email already exists."));AppUser u=new AppUser();u.setName(r.name().trim());u.setEmail(email);u.setPassword(encoder.encode(r.password()));users.save(u);return ResponseEntity.ok(payload(u));}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody AuthRequest r){if(r.email()==null||r.password()==null)return ResponseEntity.badRequest().body(Map.of("message","Email and password are required."));var u=users.findByEmail(r.email().trim().toLowerCase());if(u.isEmpty()||!encoder.matches(r.password(),u.get().getPassword()))return ResponseEntity.status(401).body(Map.of("message","Invalid email or password."));return ResponseEntity.ok(payload(u.get()));}
 private Map<String,Object> payload(AppUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"token",jwt.generate(u.getId(),u.getEmail()));}
}
