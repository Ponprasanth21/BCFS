package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualTransactionRepository extends JpaRepository<ManualTransaction, String> {
	
	 Optional<ManualTransaction> findById(String directorId);


	@Query(value = "select * from BIPS_MANUAL_TRAN_MASTER order by tran_ref_id desc", nativeQuery = true)
	List<ManualTransaction> findAllCustom();
	
	
	@Query(value = "select * from BIPS_MANUAL_TRAN_MASTER where DEL_FLAG='N' and TRAN_REF_ID=?1 ", nativeQuery = true)
	ManualTransaction findByIdCustom(String Id);


	
	//@Query(value = "SELECT to_char(tran_date,'DD-MM-YYYY')tran_date, doc_ref_id,rem_name,remit_account_no,SUM( tran_amt )tran_amt,ips_ref_no,entity_flg,entry_user,verify_user FROM bips_manual_tran_master where trunc(tran_date)=?1 GROUP BY doc_ref_id,to_char(tran_date,'DD-MM-YYYY'),rem_name,remit_account_no,ips_ref_no,entity_flg,entry_user,verify_user ORDER BY doc_ref_id asc", nativeQuery = true)
	@Query(value = "SELECT to_char(tran_date,'DD-MM-YYYY')tran_date, doc_ref_id,SUM( tran_amt )tran_amt,ips_ref_no,entity_flg,entry_user,verify_user FROM bips_manual_tran_master where trunc(tran_date)=?1 GROUP BY doc_ref_id,to_char(tran_date,'DD-MM-YYYY'),rem_name,remit_account_no,ips_ref_no,entity_flg,entry_user,verify_user ORDER BY doc_ref_id asc", nativeQuery = true)

	List<Object[]> getCustomManualList(String tranDate);

	
	@Query(value = "select * from BIPS_MANUAL_TRAN_MASTER where doc_ref_id=?1", nativeQuery = true)
	List<ManualTransaction> findByIdDocRefID(String Id);


}
