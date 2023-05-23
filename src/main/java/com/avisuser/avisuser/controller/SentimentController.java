package com.avisuser.avisuser.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.avisuser.avisuser.entities.Sentiment;
import com.avisuser.avisuser.enumeration.TypeSentiment;
import com.avisuser.avisuser.service.SentimentService;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = "sentiment",produces = APPLICATION_JSON_VALUE)
public class SentimentController {

	private SentimentService sentimentService;
	
	public SentimentController(SentimentService sentimentService) {
		this.sentimentService = sentimentService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public void creer(@RequestBody Sentiment sentiment) {
		this.sentimentService.creer(sentiment);
	}
	
	@GetMapping
	public @ResponseBody List<Sentiment> rechercher(@RequestParam(required = false) TypeSentiment type){ //@Response veut dire la liste des réponse sera transmise dans le corps de la reponse
		return this.sentimentService.rechercher(type);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "{id}")
	public void supprimer(@PathVariable int id){
	
		this.sentimentService.supprimer(id);
	}
}
