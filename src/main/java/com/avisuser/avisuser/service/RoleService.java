package com.avisuser.avisuser.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.avisuser.avisuser.entities.Role;
import com.avisuser.avisuser.enumaration.TypeDeRole;
import com.avisuser.avisuser.repository.RoleRepositoty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
	 private final RoleRepositoty roleRepositoty;

	    public void saveRole(TypeDeRole libelle) {
	        try {
	            Role role = new Role();
	            role.setLibelle(libelle);
	            roleRepositoty.save(role);
	        } catch (DataIntegrityViolationException e) {
	            // Handle data integrity violation here (e.g., data too long for column)
	            log.error("Error saving role: Data too long for the column");
	            // or
	            throw new IllegalArgumentException("Role data too long for the column");
	            // or any other appropriate handling, such as returning a custom error message
	        }
	    }
}
