package com.avisuser.avisuser.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Role;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.entities.Validation;
import com.avisuser.avisuser.enumaration.TypeDeRole;
import com.avisuser.avisuser.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService{

	private UtilisateurRepository utilisateurRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private ValidationService validationService;
	
	public void inscription(Utilisateur utilisateur) {
		if(!utilisateur.getEmail().contains("@")) {
			throw new RuntimeException("Votre mail invalide");
		}
		if(!utilisateur.getEmail().contains(".")) {
			throw new RuntimeException("Votre mail invalide");
		}
		
		Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
		if(utilisateurOptional.isPresent()) {
			throw new RuntimeException("Votre mail est déjà utilisé");
		}
		String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
		utilisateur.setMdp(mdpCrypte);
		
		Role roleUtilisateur = new Role();
		roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
		utilisateur.setRole(roleUtilisateur);
		
		utilisateur = this.utilisateurRepository.save(utilisateur);
		this.validationService.enregistrer(utilisateur);
	}

	public void activation(Map<String, String> activation) {
		Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
		if(Instant.now().isAfter(validation.getExpiration())) {
			throw new RuntimeException("Votre code est expiré");
		}
		Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId())
		.orElseThrow(()-> new RuntimeException("Utilisateur inconnu"));
	    utilisateurActiver.setActif(true);
	    this.utilisateurRepository.save(utilisateurActiver);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  //chercher un utilisateur depuis la BDD
		return this.utilisateurRepository.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException("Aucun n'utilisateur ne correspond à cette identifiant"));
	}
}
