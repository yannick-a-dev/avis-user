package com.avisuser.avisuser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.avisuser.avisuser.dto.ErrorEntity;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApplicationControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({EntityNotFoundException.class})
	public @ResponseBody ErrorEntity handleException(EntityNotFoundException e) {//@ResponseBody pour etre retourné dans le corps de la requete
		return new ErrorEntity(null, e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)//conflict lorsque deux personnes ont la meme adresse email par exemple
	@ExceptionHandler({RuntimeException.class})
	public @ResponseBody ErrorEntity handleRuntimeException(RuntimeException e) {//@ResponseBody pour etre retourné dans le corps de la requete
		return new ErrorEntity(null, e.getMessage());
	}
}
