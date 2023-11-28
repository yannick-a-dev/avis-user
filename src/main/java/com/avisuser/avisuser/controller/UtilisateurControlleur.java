package com.avisuser.avisuser.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.service.UtilisateurService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("utilisateur")
@RestController
public class UtilisateurControlleur {
	private UtilisateurService utilisateurService;
	
	@PreAuthorize("hasAnyAuthority('ADMINISTRATEUR_READ', 'MANAGER_READ')")
	@GetMapping
	public List<Utilisateur> liste() {
		return this.utilisateurService.liste();
	}
}
