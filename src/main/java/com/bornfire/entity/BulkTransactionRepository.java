package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BulkTransactionRepository extends JpaRepository<BulkTransaction, String> {
	
	 Optional<BulkTransaction> findById( String directorId);


	@Query(value = "select * from BIPS_BULK_TRAN_MASTER order by tran_ref_id desc", nativeQuery = true)
	List<BulkTransaction> findAllCustom();
	
	
	@Query(value = "select * from BIPS_BULK_TRAN_MASTER where DEL_FLAG='N' and TRAN_REF_ID=?1 ", nativeQuery = true)
	BulkTransaction findByIdCustom(String Id);
	
	@Query(value = "select * from BIPS_BULK_TRAN_MASTER where doc_ref_id=?1", nativeQuery = true)
	List<BulkTransaction> findByIdDocRefID(String Id);

	@Query(value = "SELECT to_char(tran_date,'DD-MM-YYYY')tran_date, doc_ref_id,remit_account_no,SUM( tran_amt )tran_amt,ips_ref_no,entity_flg,entry_user,verify_user FROM bips_bulk_tran_master where bulk_type='BULK_CREDIT' and trunc(tran_date)=?1 GROUP BY doc_ref_id,to_char(tran_date,'DD-MM-YYYY'),remit_account_no,ips_ref_no,entity_flg,entry_user,verify_user ORDER BY doc_ref_id asc", nativeQuery = true)
	List<Object[]> getCustomBulkCreditList(String tranDate);

	@Query(value = "SELECT to_char(tran_date,'DD-MM-YYYY')tran_date, doc_ref_id,ben_acct_name,ben_acct_no,SUM( tran_amt )tran_amt,ips_ref_no,entity_flg,entry_user,verify_user FROM bips_bulk_tran_master where bulk_type='BULK_DEBIT' and trunc(tran_date)=?1 GROUP BY doc_ref_id,to_char(tran_date,'DD-MM-YYYY'),ben_acct_name,ben_acct_no,ips_ref_no,entity_flg,entry_user,verify_user ORDER BY doc_ref_id asc", nativeQuery = true)
	List<Object[]> getCustomBulkDebitList(String tran_date);


}