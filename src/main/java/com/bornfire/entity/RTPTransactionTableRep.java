package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RTPTransactionTableRep extends JpaRepository<RTPTransactionTable, String> {
	
	Optional<RTPTransactionTable> findById(String directorId);


	@Query(value = "select * from BIPS_RTP_TRAN_MASTER order by tran_ref_id desc", nativeQuery = true)
	List<RTPTransactionTable> findAllCustom();
	
	
	@Query(value = "select * from BIPS_RTP_TRAN_MASTER where DEL_FLAG='N' and TRAN_REF_ID=?1 ", nativeQuery = true)
	RTPTransactionTable findByIdCustom(String Id);


	
	@Query(value = "SELECT to_char(tran_date,'DD-MM-YYYY')tran_date, doc_ref_id,rem_name,remit_account_no,SUM( tran_amt )tran_amt,ips_ref_no,entity_flg,entry_user,verify_user FROM BIPS_RTP_TRAN_MASTER where trunc(tran_date)=?1 GROUP BY doc_ref_id,to_char(tran_date,'DD-MM-YYYY'),rem_name,remit_account_no,ips_ref_no,entity_flg,entry_user,verify_user ORDER BY doc_ref_id asc", nativeQuery = true)
	List<Object[]> getCustomManualList(String tranDate);

	
	@Query(value = "select * from BIPS_RTP_TRAN_MASTER where doc_ref_id=?1", nativeQuery = true)
	List<RTPTransactionTable> findByIdDocRefID(String Id);


}