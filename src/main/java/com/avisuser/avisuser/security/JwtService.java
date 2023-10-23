package com.avisuser.avisuser.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {

	private final String ENCRIPTION_KEY = "074599f1f33525692e90ee91b5c87d3e0e42658c4512335c9a80c6800ed4d948";
	private UtilisateurService utilisateurService;

	public Map<String, String> generate(String username) {
		Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
		return this.generateJwt(utilisateur);
	}

	public String extractUsername(String token) {
		return this.getClaim(token, Claims::getSubject);
	}

	public boolean isTokenExpired(String token) {
		Date expirationDate = this.getClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}

	private <T> T getClaim(String token, Function<Claims, T> function) {
		Claims claims = getAllClaims(token);
		return function.apply(claims);
	}

	private Claims getAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(this.getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Map<String, String> generateJwt(Utilisateur utilisateur) {
		final long currentTime = System.currentTimeMillis();
		final long expirationTime = currentTime + 30 * 60 * 1000;
		
		 Map<String, Object> claims = new HashMap<>();
	        claims.put("nom", utilisateur.getNom());
	        claims.put("email", utilisateur.getEmail());
	        claims.put(Claims.EXPIRATION, expirationTime);
	        claims.put(Claims.SUBJECT, utilisateur.getEmail());
	        
		String bearer = Jwts.builder().setIssuedAt(new Date(currentTime)).setExpiration(new Date(expirationTime))
				.setSubject(utilisateur.getEmail()).setClaims(claims) // les infos à envoyer
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
		return Map.of("bearer", bearer);
	}

	private Key getKey() {
		byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
		return Keys.hmacShaKeyFor(decoder);
	}
}
