package com.avisuser.avisuser.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "jwt")
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String value;
	private boolean desactive;
	private boolean expire;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private RefreshToken refreshToken;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})//detaché quand on supprime, mais on ne supprime pas l'utilisateur et MERGE pour que l'utilisateur soit creer en amont avant de l'utiliser 
	@JoinColumn(name = "utilisateur_id")
	private Utilisateur utilisateur;
}
