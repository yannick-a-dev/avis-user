package com.avisuser.avisuser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.avisuser.avisuser.entities.Client;
import com.avisuser.avisuser.service.ClientService;

@RestController
@RequestMapping(path = "client")
public class ClientController {
	
	private ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping( consumes = APPLICATION_JSON_VALUE) //le post on consomme les données.
	public void creer(@RequestBody Client client) {
		this.clientService.creer(client);
	}
	
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<Client> rechercher(){
		return this.clientService.rechercher();
	}
	
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	public Client lire(@PathVariable int id) {
		return this.clientService.lire(id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(path = "{id}",consumes = APPLICATION_JSON_VALUE)
	public void modifier(@PathVariable int id,@RequestBody Client client) {
		this.clientService.modifier(id,client);
	}
}
