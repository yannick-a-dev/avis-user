package com.avisuser.avisuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class AvisUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvisUserApplication.class, args);
	}
}
