package com.avisuser.avisuser.entities;

import com.avisuser.avisuser.enumeration.TypeSentiment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SENTIMENT")
@AllArgsConstructor
@NoArgsConstructor
public class Sentiment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String texte;
	private TypeSentiment type;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}) //persist veut dire récupère la clé du client et injecte la comme clé étrangere dans sentiment,MERGE veut dire, s'il existe dejà le client, ne le cree plus mais fait une fusion
	@JoinColumn(name = "CLIENT_ID") //pour la clé étrangère
	private Client client;
}
