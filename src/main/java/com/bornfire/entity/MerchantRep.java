package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRep extends CrudRepository<Merchant,String>{
	
	@Query(value = "select * from BIPS_MERCHANT_TABLE where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
	List<Merchant> customFindByAll();
	
	@Query(value = "select * from BIPS_MERCHANT_TABLE where merchant_code=?1 and account_no=?2", nativeQuery = true)
	List<Merchant> customFindById(String merchantName,String merchantTradeName);
	
}

	
	

