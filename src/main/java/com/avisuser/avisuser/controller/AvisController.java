package com.avisuser.avisuser.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.avisuser.avisuser.entities.Avis;
import com.avisuser.avisuser.service.AvisService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("avis")
@RestController
public class AvisController {

	private final AvisService avisService;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public void creer(@RequestBody Avis avis) {
		this.avisService.creer(avis);
	}
	
	@GetMapping
	public List<Avis> liste() {
		return this.avisService.liste();
	}
}
