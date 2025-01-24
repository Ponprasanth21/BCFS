package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MandateMasterRep extends CrudRepository<MandateMaster,String>{
	
	@Query(value = "select * from BIPS_MANDATE_MASTER where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
	List<MandateMaster> customFindByAll();
	
	@Query(value = "select * from BIPS_MANDATE_MASTER where NVL(DEL_FLG,1) <> 'Y' and mand_account_no=?1 and ben_account_no=?2", nativeQuery = true)
	List<MandateMaster> customFindById(String remAcctNumber,String benAcctNumber);
	
}