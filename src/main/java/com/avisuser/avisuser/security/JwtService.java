package com.avisuser.avisuser.security;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Jwt;
import com.avisuser.avisuser.entities.RefreshToken;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.repository.JwtRepository;
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
	public static final String BEARER = "bearer";
	public static final String TOKEN_INVALIDE = "Token invalide";
	public static final String REFRESH = "refresh";
	private final String ENCRIPTION_KEY = "074599f1f33525692e90ee91b5c87d3e0e42658c4512335c9a80c6800ed4d948";
	private UtilisateurService utilisateurService;
	private JwtRepository jwtRepository;

	public Jwt tokenByValue(String value) {
      return this.jwtRepository.findByValue(value)
    		  .orElseThrow(()->new RuntimeException("Token inconnu"));
	}

	public Map<String, String> generate(String username) {
		Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
		this.disableTokens(utilisateur);
		Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(utilisateur));
		
        RefreshToken refreshToken = RefreshToken.builder()
        		.value(UUID.randomUUID().toString())
        		.expire(false)
        		.creation(Instant.now())
        		.expiration(Instant.now().plusMillis(30*60*1000))
        		.build();
		Jwt jwt = Jwt
				.builder()
				.value(jwtMap.get(BEARER))
				.desactive(false)
				.expire(false)
				.utilisateur(utilisateur)
				.refreshToken(refreshToken)
				.build();
		this.jwtRepository.save(jwt);
		jwtMap.put(REFRESH, refreshToken.getValue());
		return jwtMap;
	}


	private void disableTokens(Utilisateur utilisateur) {
		List<Jwt> jwtList = this.jwtRepository.findByUtilisateurEmail(utilisateur.getEmail());
		for (Jwt jwt : jwtList) {
		    jwt.setDesactive(true);
		    jwt.setExpire(true);
		}
		this.jwtRepository.saveAll(jwtList);

		
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
		return Jwts.parserBuilder().setSigningKey(this.getKey()).build().parseClaimsJws(token).getBody();
	}

	private Map<String, String> generateJwt(Utilisateur utilisateur) {
		final long currentTime = System.currentTimeMillis();
		final long expirationTime = currentTime + 60 * 1000;

		Map<String, Object> claims = new HashMap<>();
		claims.put("nom", utilisateur.getNom());
		claims.put("email", utilisateur.getEmail());
		claims.put(Claims.EXPIRATION, expirationTime);
		claims.put(Claims.SUBJECT, utilisateur.getEmail());

		String bearer = Jwts.builder().setIssuedAt(new Date(currentTime)).setExpiration(new Date(expirationTime))
				.setSubject(utilisateur.getEmail()).setClaims(claims) // les infos à envoyer
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
		return Map.of(BEARER, bearer);
	}

	private Key getKey() {
		byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
		return Keys.hmacShaKeyFor(decoder);
	}

	public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
		Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(()-> new RuntimeException(TOKEN_INVALIDE));
	    if(jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
	    	throw new RuntimeException(TOKEN_INVALIDE);
	    }
	    this.disableTokens(jwt.getUtilisateur());
	    return this.generate(jwt.getUtilisateur().getEmail());
	}
}
