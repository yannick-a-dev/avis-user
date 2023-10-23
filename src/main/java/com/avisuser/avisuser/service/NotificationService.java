package com.avisuser.avisuser.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.avisuser.avisuser.entities.Validation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	JavaMailSender javaMailSender;
	
	public void envoyer(Validation validation) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("no-reply@yahoo.fr");
		message.setTo(validation.getUtilisateur().getEmail());
		message.setSubject("Votre code d'activation");
		
		String texte = String.format("Bonjour %s, <br /> Votre code d'activation est %s; A bientot",
				validation.getUtilisateur().getNom(),
				validation.getCode());
		message.setText(texte);
		
		javaMailSender.send(message);
	}
}
