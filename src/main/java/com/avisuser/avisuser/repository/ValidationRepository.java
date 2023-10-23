package com.avisuser.avisuser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisuser.avisuser.entities.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

	Optional<Validation> findByCode(String code);
}
