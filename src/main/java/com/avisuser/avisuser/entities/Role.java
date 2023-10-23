package com.avisuser.avisuser.entities;

import com.avisuser.avisuser.enumaration.TypeDeRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)//stocker en chaine de caractère
	private TypeDeRole libelle;
}
