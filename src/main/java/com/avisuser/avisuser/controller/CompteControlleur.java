package com.avisuser.avisuser.controller;


import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avisuser.avisuser.dto.AuthentificationDTO;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.security.JwtService;
import com.avisuser.avisuser.service.UtilisateurService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class CompteControlleur {

	private UtilisateurService utilisateurService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	
	@PostMapping(path = "inscription")
	public void inscription(@RequestBody Utilisateur utilisateur) {
		log.info("Inscription");
		this.utilisateurService.inscription(utilisateur);
	}
	
	@PostMapping(path = "activation")
	public void activation(@RequestBody Map<String, String> activation) {
		log.info("Inscription");
		this.utilisateurService.activation(activation);
	}
	
	@PostMapping(path = "refresh-token")
	public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
		return this.jwtService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping(path = "connexion")
	public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
		Authentication authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())	
		);
		if(authenticate.isAuthenticated()) {
			return this.jwtService.generate(authentificationDTO.username());
		}
		return null;
		
	}
}
