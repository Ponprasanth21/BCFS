package com.bornfire.entity;

import java.util.Optional;

import javax.persistence.Parameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<UserProfile, String>{
	
	Optional<UserProfile> findById(String directorId);
	
	  @Query(value = "select * from AMT  where del_flg='N'", nativeQuery =
	  true)
	 Page<UserProfile> alertlist(Pageable page);
	  
	  
	 @Modifying
	@Query(value = "UPDATE PARAMETER_TABLE u set u.DEL_FLG ='Y' where u.SRL_NO =?1", nativeQuery = true)
	int findById1(String srl_no);
	  
	

}