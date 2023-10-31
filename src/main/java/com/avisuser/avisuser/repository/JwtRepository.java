package com.avisuser.avisuser.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.avisuser.avisuser.entities.Jwt;

public interface JwtRepository extends JpaRepository<Jwt, Integer> {

	Optional<Jwt> findByValueAndDesactiveAndExpire(String value, boolean desactive, boolean expire);
	
	Optional<Jwt> findByValue(String value);
	
	@Query("SELECT j FROM Jwt j WHERE j.utilisateur.email = :email")
	List<Jwt> findByUtilisateurEmail(@Param("email") String email);
	
	@Query("SELECT j FROM Jwt j WHERE j.refreshToken.value = :value")
	Optional<Jwt> findByRefreshToken(@Param("value") String value);

	void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}
