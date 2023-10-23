package com.avisuser.avisuser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisuser.avisuser.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

	Optional<Utilisateur> findByEmail(String email);

}
