package com.bornfire.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

 


@Transactional
@Repository
public interface TranCimCBSTableRep extends JpaRepository<TranCimCBSTable, String> {

	@Query(value = "Select * from(select * from BIPS_TRAN_CIM_CBS_TABLE where sequence_unique_id=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where sequence_unique_id=?1 )order by tran_date asc ", nativeQuery = true)
	List<TranCimCBSTable> findAllCustom(String seqUniqueID);
	
	@Query(value = "select * from(select * from BIPS_TRAN_CIM_CBS_TABLE where tran_no=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where tran_no=?1) order by tran_date desc ", nativeQuery = true)
	List<TranCimCBSTable> findAllCustomTranAudit(String tranAuditNumber);
	
	@Query(value = "select * from (select * from BIPS_TRAN_CIM_CBS_TABLE where request_uuid=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where request_uuid=?1)order by tran_date desc ", nativeQuery = true)
	TranCimCBSTable findAllTranAudit(String tranAuditNumber);

	@Query(value = "select * from(select * from BIPS_TRAN_CIM_CBS_TABLE where sequence_unique_id=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where sequence_unique_id=?1) order by tran_date desc ", nativeQuery = true)
	List<TranCimCBSTable> findAllBulkCredit(String seqUniqueID);
	
	@Query(value = "select * from (select * from BIPS_TRAN_CIM_CBS_TABLE where sequence_unique_id=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where sequence_unique_id=?1 )order by tran_date desc ", nativeQuery = true)
	List<TranCimCBSTable> findAllBulkDebit(String seqUniqueID);

	@Query(value = "select * from(select * from BIPS_TRAN_CIM_CBS_TABLE where trunc(tran_date)=?1 UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where trunc(tran_date)=?1 )order by tran_date desc ", nativeQuery = true)
	List<TranCimCBSTable> findByCustomList(String tranDate);
	
/////Payable CBS Amount
	@Query(value = "select (select  nvl(sum(tran_amount),0) from (select * BIPS_TRAN_CIM_CBS_TABLE where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE') UNION ALL select * BIPS_TRAN_CIM_CBS_HIST_TABLE where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE')))- (select  nvl(sum(tran_amount),0) from (select * from bips_tran_cim_cbs_table where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE') UNION ALL select * from bips_tran_cim_cbs_hist_table where trunc(tran_date)=?1 and settl_acct_type='PAYABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE'))) amount from dual", nativeQuery = true)
	String  findByCustomPayAmt(String tranDate);
	
    /////Receivable CBS Amount
	@Query(value = "select (select  nvl(sum(tran_amount),0) from (select * from BIPS_TRAN_CIM_CBS_TABLE where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE') UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type not like('%REVERSE')))- (select  nvl(sum(tran_amount),0) from (select * from bips_tran_cim_cbs_table where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE')) UNION ALL select * from bips_tran_cim_cbs_hist_table where trunc(tran_date)=?1 and settl_acct_type='RECEIVABLE' and tran_cbs_status='SUCCESS' and tran_type like('%REVERSE') )amount from dual", nativeQuery = true)
	String  findByCustomRecAmt(String tranDate);
	
    /////Payable Settlement CBS Amount
	@Query(value = "select  nvl(sum(tran_amount),0)amount from (select * from BIPS_TRAN_CIM_CBS_TABLE where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='PAYABLE' and tran_cbs_status='SUCCESS' UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='PAYABLE' and tran_cbs_status='SUCCESS')", nativeQuery = true)
	String  findByCustomSettlPayAmt(String tranDate);
	
    /////Receivable Settlement CBS Amount
	@Query(value = "select  nvl(sum(tran_amount),0)amount from (select * from BIPS_TRAN_CIM_CBS_TABLE where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='RECEIVABLE' and tran_cbs_status='SUCCESS' UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where trunc(tran_date)=?1 and settl_acct_type='SETTLEMENT' and tran_type='RECEIVABLE' and tran_cbs_status='SUCCESS')", nativeQuery = true)
	String  findByCustomSettlRecAmt(String tranDate);
	
	
	@Query(value = "select * from (select * from BIPS_TRAN_CIM_CBS_TABLE where sequence_unique_id=?1 and((tran_type='DR' and isreversal='N')OR (tran_type='CR' and isreversal='R'))  order by message_date_time desc UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where sequence_unique_id=?1 and((tran_type='DR' and isreversal='N')OR (tran_type='CR' and isreversal='R'))  order by message_date_time desc)", nativeQuery = true)
	List<TranCimCBSTable> findAllCustomOutgoing(String seqUniqueID);
	
	@Query(value = "select * from (select * from BIPS_TRAN_CIM_CBS_TABLE where sequence_unique_id=?1 and((tran_type='CR' and isreversal='N')OR (tran_type='DR' and isreversal='R'))  order by message_date_time desc UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where sequence_unique_id=?1 and((tran_type='CR' and isreversal='N')OR (tran_type='DR' and isreversal='R'))  order by message_date_time desc)", nativeQuery = true)
	List<TranCimCBSTable> findAllCustomIncoming(String seqUniqueID);

	@Query(value = "select * from (select * from BIPS_TRAN_CIM_CBS_TABLE where (sequence_unique_id=?1 or sequence_unique_id=?2)UNION ALL select * from BIPS_TRAN_CIM_CBS_HIST_TABLE where (sequence_unique_id=?1 or sequence_unique_id=?2)) order by tran_date asc ", nativeQuery = true)
	List<TranCimCBSTable> findAllCustomBulkCredit(String seqenceNumberFormat1, String seqenceNumberFormat2);
	
	
	
}
