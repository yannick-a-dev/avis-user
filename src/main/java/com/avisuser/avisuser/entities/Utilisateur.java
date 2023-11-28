package com.avisuser.avisuser.entities;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "mot_de_passe")
	private String mdp;
	private String nom;
	private String email;
	private boolean actif = false;
	@OneToOne(cascade = CascadeType.ALL)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.role.getLibelle().getAuthorities();
	}

	@Override
	public String getPassword() {
		return this.mdp;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.actif;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.actif;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.actif;
	}

	@Override
	public boolean isEnabled() {
		return this.actif;
	}
}
