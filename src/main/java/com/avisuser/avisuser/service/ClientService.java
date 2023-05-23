package com.avisuser.avisuser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Client;
import com.avisuser.avisuser.repository.ClientRepository;

@Service
public class ClientService {

	private ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public void creer(Client client) {
		Client client1 = this.clientRepository.findByEmail(client.getEmail());
		if (client1 == null) {
			this.clientRepository.save(client);
		}
	}

	public List<Client> rechercher() {
		return this.clientRepository.findAll();
	}

	public Client lire(int id) {
		Optional<Client> client = this.clientRepository.findById(id);
		if(client.isPresent()) {
			return client.get();
		}
		return null;
	}

	public Client lireOuCreer(Client clientAcreer) {
		Client client1 = this.clientRepository.findByEmail(clientAcreer.getEmail());
		if (client1 == null) {
		   client1 = this.clientRepository.save(clientAcreer);
		}
		return client1;
	}

	public void modifier(int id, Client client) {
		Client client2 = this.lire(id);
		if(client2.getId()== client.getId()) {
		  client2.setEmail(client.getEmail());
		  client2.setTelephone(client.getTelephone());
		  this.clientRepository.save(client2);
		}
	}
}
