package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ConsentOutwardAccessRep extends JpaRepository<ConsentOutwardAccess, String> {
 
	Optional<ConsentOutwardAccess> findById(String directorId);

	@Query(value = "select * from BIPS_CONSENT_OUTWARD_ACCESS_TABLE ", nativeQuery = true)
	List<ConsentOutwardAccess> getinquiry();

}
