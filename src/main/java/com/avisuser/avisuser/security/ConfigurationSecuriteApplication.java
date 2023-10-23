package com.avisuser.avisuser.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteApplication {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtFilter jwtFilter;

	public ConfigurationSecuriteApplication(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return 
				httpSecurity
				        .csrf(AbstractHttpConfigurer::disable)
				        .authorizeHttpRequests(
				        	authorize -> authorize
				        	                     .requestMatchers(HttpMethod.POST,"/inscription").permitAll()
				        	                     .requestMatchers(HttpMethod.POST,"/activation").permitAll()
				        	                     .requestMatchers(HttpMethod.POST,"/connexion").permitAll()
				        	                     .anyRequest().authenticated()
				        )
				        .sessionManagement(httpSecuritySessionManagementConfigurer ->
				        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //STATELESS ne garde pas l'info pour utilisateur par exemple
				        )
				        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				        .build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {    //pour l'autentification
		return authenticationConfiguration.getAuthenticationManager(); 	
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) { //pour l'accès à la BDD
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return daoAuthenticationProvider;
	}
}
