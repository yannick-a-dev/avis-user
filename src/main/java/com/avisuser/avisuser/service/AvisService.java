package com.avisuser.avisuser.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.avisuser.avisuser.entities.Avis;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.repository.AvisRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AvisService {

	private final AvisRepository avisRepository;
	
	public void creer(Avis avis) {
		Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		avis.setUtilisateur(utilisateur);
		this.avisRepository.save(avis);
	}

	public List<Avis> liste() {
		return this.avisRepository.findAll();
		
	}

}
