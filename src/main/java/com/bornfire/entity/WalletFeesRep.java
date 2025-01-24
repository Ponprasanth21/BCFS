package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletFeesRep extends CrudRepository<WalletFees,String>{
	
	@Query(value = "select * from WALLET_FEES_CHARGES_PARM where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
	List<WalletFees> customFindByAll();
	
	@Query(value = "select * from WALLET_FEES_CHARGES_PARM where FEE_SRL_NO=?1 and WALLET_TRAN_TYPE=?2", nativeQuery = true)
	List<WalletFees> customFindById(String feesSrlNo,String walletTranType);
}

	
	

