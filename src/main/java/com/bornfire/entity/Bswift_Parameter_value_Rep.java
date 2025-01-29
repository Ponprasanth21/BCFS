package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Bswift_Parameter_value_Rep extends JpaRepository<Bswift_Parameter_value_Entity, String>{
	@Query(value = "select * from SWIFT_DETAILS ", nativeQuery = true)
	List<Bswift_Parameter_value_Entity> findAllCustomvalue();
}
