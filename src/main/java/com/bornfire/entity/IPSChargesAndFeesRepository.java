package com.bornfire.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IPSChargesAndFeesRepository extends JpaRepository<IPSChargesAndFees, String> {
	
	 Optional<IPSChargesAndFees> findById(String directorId);


	@Query(value = "select * from BIPS_CHARGES_AND_FEES_TABLE where del_flg='N'", nativeQuery = true)
	List<IPSChargesAndFees> findAllCustom();
	
	
	@Query(value = "select * from BIPS_CHARGES_AND_FEES_TABLE where reference_number= ?1 ", nativeQuery = true)
	IPSChargesAndFees findByIdReferenceNum(String Id);


}
