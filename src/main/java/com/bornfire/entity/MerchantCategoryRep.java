package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCategoryRep extends JpaRepository<MerchantCategoryCodeEntity, String> {
	
	 Optional<MerchantCategoryCodeEntity> findById( String directorId);


		@Query(value = "select * from BIPS_MERCHANT_CATEGORY_CODE_TABLE where del_flg<>'Y' order by merchant_code", nativeQuery = true)
		List<MerchantCategoryCodeEntity> findAllCustom();
		
		@Query(value = "select * from BIPS_MERCHANT_CATEGORY_CODE_TABLE where merchant_code=?1", nativeQuery = true)
		MerchantCategoryCodeEntity getlist(String merchant);
		
		@Query(value = "select count(*) from BIPS_MERCHANT_CATEGORY_CODE_TABLE where merchant_code=?1", nativeQuery = true)
		String getlistcount(String merchant);
}
