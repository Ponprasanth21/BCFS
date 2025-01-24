package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface  WalletTranMasterRep extends JpaRepository<WalletTranMasterTable,String> {

	@Query(value = "select * from BIPS_WALLET_TRAN_TABLE where bips_acct_no=?3 and trunc(tran_date) between ?1 and ?2  order by tran_date desc", nativeQuery = true)
	List<WalletTranMasterTable> findAllCustom(String date1, String date12,String acctNo);
	

	@Query(value = "select * from BIPS_WALLET_TRAN_TABLE where bips_acct_no=?1", nativeQuery = true)
	List<WalletTranMasterTable> findAllCustom1(String date1);

	@Query(value = "select * from BIPS_WALLET_TRAN_TABLE", nativeQuery = true)
	List<WalletTranMasterTable> findAllCustom2();
	
	@Query(value = "select * from BIPS_WALLET_TRAN_TABLE where  trunc(tran_date) between ?1 and ?2  order by tran_date desc", nativeQuery = true)
	List<WalletTranMasterTable> findAllCustomList(String date1, String date12);
	

}
