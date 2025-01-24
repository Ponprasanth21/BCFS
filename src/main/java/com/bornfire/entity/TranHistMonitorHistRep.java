package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TranHistMonitorHistRep extends JpaRepository<TranMonitoringHist, String> {
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where msg_type='RTP' and  trunc(tran_date)=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomRTP(String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where msg_type='OUTGOING' and  trunc(tran_date)=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomMConnect(String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where msg_type='INCOMING' and  trunc(tran_date)=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomIncome(String tranDate);
	
	@Query(value = "select cim_account,cim_account_name,ipsx_account,ipsx_account_name,tran_date,value_date,sequence_unique_id,tran_audit_number,msg_type,tran_amount,entry_user,instr_id,(select a.bank_name from bips_other_bank_agent_table a where a.bank_agent=cdtr_agt)cdtr_agt,(select a.bank_name from bips_other_bank_agent_table a where a.bank_agent=dbtr_agt)dbtr_agt,(select a.bank_name from bips_other_bank_agent_table a where a.bank_agent=instg_agt)instg_agt,tran_currency,tran_status,cbs_status,cbs_status_error,response_status,ipsx_status_error  from BIPS_TRANSACTION_HIST_MONITORING_TABLE where msg_type='INCOMING' and  trunc(tran_date)=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<Object[]> findAllCustomIncome1(String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where (msg_type='BULK_CREDIT' OR msg_type='BULK_DEBIT') and  trunc(tran_date)=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomBulk(String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where tran_date=?1 and tran_status=?2 order by tran_date desc", nativeQuery = true)
	List<TranMonitoringHist> findAllCustom1(String tranDate,String tranStatus);

	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where cim_account=?1 and msg_type='RTP' and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomRTPFromMyt(String acctNumber);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where tran_date>=?1 and tran_date is not null order by tran_date desc ", nativeQuery = true)
	List<TranMonitoringHist> findAllCustom(String tranDate);

	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where master_ref_id=?1 and  trunc(tran_date)=?2", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomBulkCredit(String seqUniqueID, String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where master_ref_id=?1 and  trunc(tran_date)=?2", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomBulkDebit(String seqUniqueID, String tranDate);
	
	@Query(value = "select * from BIPS_TRANSACTION_HIST_MONITORING_TABLE where master_ref_id=?1 and  trunc(tran_date)=?2", nativeQuery = true)
	List<TranMonitoringHist> findAllCustomBulkManual(String seqUniqueID, String tranDate);

	@Query(value = "select CIM_ACCOUNT,CIM_ACCOUNT_NAME,IPSX_ACCOUNT,IPSX_ACCOUNT_NAME,TRAN_DATE,VALUE_DATE,SEQUENCE_UNIQUE_ID,TRAN_AUDIT_NUMBER,MSG_TYPE,TRAN_AMOUNT,TRAN_CURRENCY,ENTRY_USER,TRAN_STATUS,CBS_STATUS,CBS_STATUS_ERROR,RESPONSE_STATUS,IPSX_STATUS_ERROR from bips_outward_transaction_hist_monitoring_table where cbs_status='CBS_DEBIT_OK' and tran_status='FAILURE' and trunc(tran_date)=?1 union all select  CIM_ACCOUNT,CIM_ACCOUNT_NAME,IPSX_ACCOUNT,IPSX_ACCOUNT_NAME,TRAN_DATE,VALUE_DATE,SEQUENCE_UNIQUE_ID,TRAN_AUDIT_NUMBER,MSG_TYPE,TRAN_AMOUNT,TRAN_CURRENCY,ENTRY_USER,TRAN_STATUS,CBS_STATUS,CBS_STATUS_ERROR,RESPONSE_STATUS,IPSX_STATUS_ERROR from bips_transaction_hist_monitoring_table where cbs_status='CBS_CREDIT_OK' and tran_status='FAILURE'  and trunc(tran_date)=?1", nativeQuery = true)
	List<Object[]> findAllCustomReverse(String tranDate);
	
	@Query(value = "select tran_date,sequence_unique_id,cim_account,tran_currency,tran_amount from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='OUTGOING'", nativeQuery = true)
	List<Object[]> findAllCustomTranOutwardRecon(String tranDate);

	@Query(value = "select tran_date,sequence_unique_id,cim_account,tran_currency,tran_amount from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='INCOMING'", nativeQuery = true)
	List<Object[]> findAllCustomTranInwardRecon(String tranDate);
	
	@Query(value = "select tran_date,sequence_unique_id,cim_account,tran_currency,tran_amount from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='RTP'", nativeQuery = true)
	List<Object[]> findAllCustomTranRTPRecon(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0)as Count from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='INCOMING'", nativeQuery = true)
	String countInwardReconcillation(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='RTP'", nativeQuery = true)
	String countOutwardReconcillation(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='RTP' or msg_type='INCOMING') and( (tran_status='SUCCESS' and (cbs_status='CBS_DEBIT_ERROR' or cbs_status='CBS_CREDIT_ERROR'))or (tran_status='FAILURE' and (cbs_status='CBS_DEBIT_OK' or cbs_status='CBS_CREDIT_OK'))or(tran_status!='SUCCESS' and tran_status!='FAILURE'))", nativeQuery = true)
	String countUnmatchReconcillation(String tranDate);
	
	@Query(value = "select  nvl(sum(tran_amount),0)as Count from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='FAILURE' and (cbs_status='CBS_DEBIT_REVERSE_OK' or cbs_status='CBS_CREDIT_REVERSE_OK')", nativeQuery = true)
	String countOffsetReconcillation(String tranDate);

//	@Query(value = "select nvl(sum(entry_entrydtl_btch_tot_amt),0)as Count from bips_settlement_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='CRDT'", nativeQuery = true)
//	String countSettlBOMInward(String tranDate);
	
	@Query(value = "select nvl(sum(entry_entrydtl_btch_tot_amt),0)as Count from (select  entry_entrydtl_btch_tot_amt from bips_settlement_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='CRDT' UNION ALL select entry_entrydtl_btch_tot_amt from bips_settlement_hist_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='CRDT')", nativeQuery = true)
	String countSettlBOMInward(String tranDate);
	
//	@Query(value = "select nvl(sum(entry_entrydtl_btch_tot_amt),0)as Count from bips_settlement_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='DBIT'", nativeQuery = true)
//	String countSettlBOMOutward(String tranDate);
	
	@Query(value = "select nvl(sum(entry_entrydtl_btch_tot_amt),0)as Count from (select  entry_entrydtl_btch_tot_amt from bips_settlement_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='DBIT' UNION ALL select entry_entrydtl_btch_tot_amt from bips_settlement_hist_report where  value_date=?1 and entry_entrydtl_btch_cd_dbt_ind='DBIT')", nativeQuery = true)
	String countSettlBOMOutward(String tranDate);

	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='INCOMING'", nativeQuery = true)
	List<TranMonitoringHist> inwardReconcillationList(String tranDate);
	
	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and msg_type='RTP'", nativeQuery = true)
	List<TranMonitoringHist> outwardReconcillationList(String tranDate);
	
//	@Query(value = "SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_report b where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='CRDT' and b.entry_entrydtl_btch_msg_id=a.sequence_unique_id", nativeQuery = true)
//	List<Object[]> inwardSettlList(String tranDate);
	
	@Query(value = "select * from( SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_report b where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='CRDT' and b.entry_entrydtl_btch_msg_id=a.sequence_unique_id UNION ALL  SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_hist_report b where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='CRDT' and b.entry_entrydtl_btch_msg_id=a.sequence_unique_id)", nativeQuery = true)
	List<Object[]> inwardSettlList(String tranDate);
	
//	@Query(value = "SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id) union all SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_outward_transaction_hist_monitoring_table a,bips_settlement_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id)", nativeQuery = true)
//	List<Object[]> outwardSettlList(String tranDate);
	
	@Query(value = "SELECT * FROM ((SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id) union all SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_outward_transaction_hist_monitoring_table a,bips_settlement_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id))UNION ALL (SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_transaction_hist_monitoring_table a,bips_settlement_hist_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id) union all SELECT a.tran_date,a.value_date,a.msg_type,b.entry_entrydtl_btch_cd_dbt_ind,a.cim_account,a.cim_account_name,a.ipsx_account,a.ipsx_account_name,b.entry_entrydtl_btch_msg_id,a.tran_audit_number,b.entry_entrydtl_btch_tot_amt,b.entry_entrydtl_btch_cncy,a.tran_status from bips_outward_transaction_hist_monitoring_table a,bips_settlement_hist_report b  where b.value_date=?1 and b.entry_entrydtl_btch_cd_dbt_ind='DBIT' and (b.entry_entrydtl_btch_msg_id=a.sequence_unique_id or b.entry_entrydtl_btch_msg_id=a.cim_message_id)))", nativeQuery = true)
	List<Object[]> outwardSettlList(String tranDate);

	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (cbs_status='CBS_DEBIT_REVERSE_OK' or cbs_status='CBS_CREDIT_REVERSE_OK')", nativeQuery = true)
	List<TranMonitoringHist> offsetReconcillationList(String tranDate);

	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='RTP' or msg_type='INCOMING') and( (tran_status='SUCCESS' and (cbs_status='CBS_DEBIT_ERROR' or cbs_status='CBS_CREDIT_ERROR'))or (tran_status='FAILURE' and (cbs_status='CBS_DEBIT_OK' or cbs_status='CBS_CREDIT_OK'))or(tran_status!='SUCCESS' and tran_status!='FAILURE'))", nativeQuery = true)
	List<TranMonitoringHist> unmatchReconcillationList(String tranDate);

	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='FAILURE' and  (cbs_status!='CBS_DEBIT_OK' and cbs_status!='CBS_CREDIT_OK'  and cbs_status!='CBS_DEBIT_REVERSE_OK' and cbs_status!='CBS_CREDIT_REVERSE_OK')", nativeQuery = true)
	List<TranMonitoringHist> failedReconcillationList(String tranDate);

	@Query(value = "select * from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1", nativeQuery = true)
	List<TranMonitoringHist> totalReconcillationList(String tranDate);

	/*@Query(value = "select a.bank_code,a.bank_name,(select count(*) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent) )out_no_of_txs," + 
			" (select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent) )out_tot_amt ," + 
			" (select count(*) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type ='INCOMING' and (instg_agt=a.bank_agent) )in_no_of_txs," + 
			" (select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type='INCOMING' and (instg_agt=a.bank_agent ))in_tot_amt from bips_other_bank_agent_table a", nativeQuery = true)
	List<Object[]> snapShotRecord(String tranDate);*/
	
	@Query(value = "select a.bank_code,a.bank_name,((select count(*) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent))+  (select count(*) from bips_outward_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent)))out_no_of_txs,((select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent  ))+ (select nvl(sum(tran_amount),0) from bips_outward_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type!='INCOMING' and (cdtr_agt=a.bank_agent  )))out_tot_amt , (select count(*) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type ='INCOMING' and (instg_agt=a.bank_agent) )in_no_of_txs, (select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and msg_type='INCOMING' and (instg_agt=a.bank_agent ))in_tot_amt from bips_other_bank_agent_table a where a.del_flg<>'Y'", nativeQuery = true)
	List<Object[]> snapShotRecord(String tranDate);


	

////MC
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCListTotTxs(String tranDate);
	
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCLisSucTxs(String tranDate);

	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCListFailTxs(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCListTotAmt(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCLisSucAmt(String tranDate);

	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
	String outwardMCListFailAmt(String tranDate);
	
	
	
	///RTP
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPListTotTxs(String tranDate);
	
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPLisSucTxs(String tranDate);

	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPListFailTxs(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPListTotAmt(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPLisSucAmt(String tranDate);

	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='RTP')", nativeQuery = true)
	String outwardRTPListFailAmt(String tranDate);
	
	///Inward
	
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCListTotTxs(String tranDate);
	
	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCLisSucTxs(String tranDate);

	@Query(value = "select nvl(count(sequence_unique_id),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCListFailTxs(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCListTotAmt(String tranDate);
	
	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCLisSucAmt(String tranDate);

	@Query(value = "select nvl(sum(tran_amount),0) from bips_transaction_hist_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='INCOMING')", nativeQuery = true)
	String outwardINCListFailAmt(String tranDate);
	

	///OUTGOING
		@Query(value = "select nvl(count(sequence_unique_id),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTListTotTxs(String tranDate);
		
		@Query(value = "select nvl(count(sequence_unique_id),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTLisSucTxs(String tranDate);

		@Query(value = "select nvl(count(sequence_unique_id),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTListFailTxs(String tranDate);
		
		@Query(value = "select nvl(sum(tran_amount),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTListTotAmt(String tranDate);
		
		@Query(value = "select nvl(sum(tran_amount),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and tran_status='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTLisSucAmt(String tranDate);

		@Query(value = "select nvl(sum(tran_amount),0) from bips_outward_transaction_monitoring_table where trunc(tran_date)=?1 and tran_status!='SUCCESS' and (msg_type='OUTGOING')", nativeQuery = true)
		String outwardOUTListFailAmt(String tranDate);
	

}
