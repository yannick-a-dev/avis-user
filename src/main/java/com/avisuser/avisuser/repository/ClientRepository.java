package com.avisuser.avisuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisuser.avisuser.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByEmail(String email);
}
