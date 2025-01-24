package com.bornfire.entity;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Transactional
@Repository
public interface TranCBSTableRep extends JpaRepository<TranCBSTable, String> {

	@Query(value = "select * from BIPS_TRAN_CBS_TABLE where sequence_unique_id=?1 order by tran_date desc ", nativeQuery = true)
	List<TranCBSTable> findAllCustom(String seqUniqueID);
	
	@Query(value = "select * from BIPS_TRAN_CBS_TABLE where tran_audit_number=?1 order by tran_date desc ", nativeQuery = true)
	TranCBSTable findAllTranAudit(String tranAuditNumber);

	@Query(value = "select * from BIPS_TRAN_CBS_TABLE where sequence_unique_id=?1 order by tran_date desc ", nativeQuery = true)
	List<TranCBSTable> findAllBulkCredit(String seqUniqueID);
	
	@Query(value = "select * from BIPS_TRAN_CBS_TABLE where sequence_unique_id=?1 order by tran_date desc ", nativeQuery = true)
	List<TranCBSTable> findAllBulkDebit(String seqUniqueID);

	@Query(value = "select * from BIPS_TRAN_CBS_TABLE where trunc(tran_date)=?1 order by tran_date desc ", nativeQuery = true)
	List<TranCBSTable> findByCustomList(String tranDate);
	
	
	

	/////Payable CBS Amount
	@Query(value = "select (select  nvl(sum(tran_amount),0) from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE'))- (select  nvl(sum(tran_amount),0) from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE')) amount from dual", nativeQuery = true)
	String  findByCustomPayAmt(String tranDate);
	
    /////Receivable CBS Amount
	@Query(value = "select (select  nvl(sum(tran_amount),0) from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE'))- (select  nvl(sum(tran_amount),0) from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE')) amount from dual", nativeQuery = true)
	String  findByCustomRecAmt(String tranDate);
	
    /////Payable Settlement CBS Amount
	@Query(value = "select  nvl(sum(tran_amount),0)amount from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='PAYABLE' and tran_cbs_status='SUCCESS'", nativeQuery = true)
	String  findByCustomSettlPayAmt(String tranDate);
	
    /////Receivable Settlement CBS Amount
	@Query(value = "select  nvl(sum(tran_amount),0)amount from bips_tran_cbs_table where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='RECEIVABLE' and tran_cbs_status='SUCCESS'", nativeQuery = true)
	String  findByCustomSettlRecAmt(String tranDate);
}

