package com.avisuser.avisuser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.avisuser.avisuser.entities.Role;
import com.avisuser.avisuser.entities.Utilisateur;
import com.avisuser.avisuser.enumaration.TypeDeRole;
import com.avisuser.avisuser.repository.UtilisateurRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class AvisUserApplication implements CommandLineRunner{
    
	private UtilisateurRepository utilisateurRepository;
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AvisUserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Utilisateur admin = Utilisateur.builder()
				.actif(true)
				.nom("admin")
				.mdp(passwordEncoder.encode("admin"))
				.email("essolayk@yahoo.fr")
				.role(
					Role.builder()
					   .libelle(TypeDeRole.ADMINISTRATEUR)
					   .build()
				)
				.build();
		admin = this.utilisateurRepository.findByEmail("essolayk@yahoo.fr")
		    .orElse(admin);
		this.utilisateurRepository.save(admin);
		
		Utilisateur manager = Utilisateur.builder()
				.actif(true)
				.nom("manager")
				.mdp(passwordEncoder.encode("manager"))
				.email("manageryk@yahoo.fr")
				.role(
					Role.builder()
					   .libelle(TypeDeRole.MANAGER)
					   .build()
				)
				.build();
		manager = this.utilisateurRepository.findByEmail("manageryk@yahoo.fr")
			    .orElse(manager);
			this.utilisateurRepository.save(manager);
		
	}
}
