package com.bornfire.entity;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface  WalletMasterRep extends JpaRepository<WalletMasterTable,String> {

	@Query(value = "select * from BIPS_WALLET_MASTER where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
	List<WalletMasterTable> customFindByAll();

	@Query(value = "select * from BIPS_WALLET_MASTER where acct_no=?1", nativeQuery = true)
	WalletMasterTable findByIdCustom(String acctNo);

	static void save(WalletFees up) {
		// TODO Auto-generated method stub
		
	}
	

}
