package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface FORM_TRANSFER_REP extends JpaRepository<FORM_TRANSFER_ENTITY, String>{
	@Query(value = "select * from FORM_TRANSFER WHERE DEL_FLG = 'N' ", nativeQuery = true)
	List<FORM_TRANSFER_ENTITY> findAllCustom();
	
	@Query(value = "select * from FORM_TRANSFER where srl_num = ?1", nativeQuery = true)
	FORM_TRANSFER_ENTITY findparavalue(String srl_num);
}
