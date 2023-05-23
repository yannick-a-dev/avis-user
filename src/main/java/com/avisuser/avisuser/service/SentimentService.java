package com.avisuser.avisuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Client;
import com.avisuser.avisuser.entities.Sentiment;
import com.avisuser.avisuser.enumeration.TypeSentiment;
import com.avisuser.avisuser.repository.SentimentRepository;

@Service
public class SentimentService {
	
	private ClientService clientService;
	private SentimentRepository sentimentRepository;

	
	public SentimentService(ClientService clientService, SentimentRepository sentimentRepository) {
		this.clientService = clientService;
		this.sentimentRepository = sentimentRepository;
	}


	public void creer(Sentiment sentiment) {
		Client client = this.clientService.lireOuCreer(sentiment.getClient());
		sentiment.setClient(client);
		//Analyse le type avec par exemple une api externe
		
		if(sentiment.getTexte().contains("pas")) {
			sentiment.setType(TypeSentiment.NEGATIF);
		}else {
			sentiment.setType(TypeSentiment.POSITIF);
		}
		this.sentimentRepository.save(sentiment);
	}


	public List<Sentiment> rechercher(TypeSentiment type) {
		if(type==null) {
			return this.sentimentRepository.findAll();
		}else {
			return this.sentimentRepository.findByType(type);
		}	
	}


	public void supprimer(int id) {
		this.sentimentRepository.deleteById(id);
		
	}
	
	
}
