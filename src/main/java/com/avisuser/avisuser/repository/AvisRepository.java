package com.avisuser.avisuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisuser.avisuser.entities.Avis;

public interface AvisRepository extends JpaRepository<Avis, Integer> {

}
