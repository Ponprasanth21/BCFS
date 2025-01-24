
package com.bornfire.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface TranDetailsRepository extends JpaRepository<TransactionAdmin, String>{
 

		  
	@Query(value = "select count(*) from trans_details", nativeQuery = true)
	long findtrancount();
	
	
	@Query(value = "select count(*) from trans_details where tran_status='SUCCESS'", nativeQuery = true)
	long findtransucescount();
	
	@Query(value = "select count(*) from trans_details where tran_status='FAILED'", nativeQuery = true)
	long findtranfailurecount();
}
