package com.id.akn.config;

import java.util.Date;
import javax.crypto.SecretKey;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	@Value("${JWT_SECRET_KEY}")
	private String jwtSecret;
	
	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	public String generateToken(Authentication auth) {
		String role = auth.getAuthorities().stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElse("ROLE_USER");

		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", auth.getName())
				.claim("role", role)
				.signWith(this.key)
				.compact();
		return jwt;
	}

	public String getEmailFromToken(String jwt) {
		if (jwt != null && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7);
		}
		
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(this.key)
				.build()
				.parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));
		return email;
	}
}