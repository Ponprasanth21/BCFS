package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Bswift_Parameter_Rep extends JpaRepository<Bswift_Parameter_Entity, String>{
	@Query(value = "select * from BSWIFT_PARAMETER_TABLE WHERE DEL_FLG = 'N' ", nativeQuery = true)
	List<Bswift_Parameter_Entity> findAllCustom();
	
	@Query(value = "select * from BSWIFT_PARAMETER_TABLE where srl_num = ?1", nativeQuery = true)
	Bswift_Parameter_Entity findparavalue(String srl_num);
	
	@Query(value = "SELECT parameter_seq.nextval FROM dual", nativeQuery = true)
	String getNextSeriesId();
}
