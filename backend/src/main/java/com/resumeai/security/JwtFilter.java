package com.resumeai.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.io.IOException; import java.util.List;
@Component public class JwtFilter extends OncePerRequestFilter {
 private final JwtService jwt; public JwtFilter(JwtService jwt){this.jwt=jwt;}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization"); if(h!=null&&h.startsWith("Bearer "))try{String t=h.substring(7);String email=jwt.email(t);Long uid=jwt.userId(t);var auth=new UsernamePasswordAuthenticationToken(email,null,List.of());auth.setDetails(uid);SecurityContextHolder.getContext().setAuthentication(auth);}catch(Exception ignored){} chain.doFilter(req,res);
 }
}
