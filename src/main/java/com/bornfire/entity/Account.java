package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



//@Entity
//@Table(name = "GAM")

public class Account {
	
	
	
	private String	acid;
	private String	entity_cre_flg;
	private String	del_flg;
	private String	sol_id;
	private String	acct_prefix;
	private String	acct_num;
	private String	bacid;
	private String	foracid;
	private String	acct_name;
	private String	acct_short_name;
	//@Id
	private String	cust_id;
	private String	emp_id;
	private String	gl_sub_head_code;
	private String	acct_ownership;
	private String	schm_code;
	private BigDecimal	dr_bal_lim;
	private String	acct_rpt_code;
	private String	frez_code;
	private String	frez_reason_code;
	private Date	acct_opn_date;
	private String	acct_cls_flg;
	private Date	acct_cls_date;
	private BigDecimal	clr_bal_amt;
	private BigDecimal	tot_mod_times;
	private BigDecimal	ledg_num;
	private BigDecimal	un_clr_bal_amt;
	private BigDecimal	drwng_power;
	private BigDecimal	sanct_lim;
	private BigDecimal	adhoc_lim;
	private BigDecimal	emer_advn;
	private BigDecimal	dacc_lim;
	private BigDecimal	system_reserved_amt;
	private BigDecimal	single_tran_lim;
	private BigDecimal	clean_adhoc_lim;
	private BigDecimal	clean_emer_advn;
	private BigDecimal	clean_single_tran_lim;
	private BigDecimal	system_gen_lim;
	private String	chq_alwd_flg;
	private BigDecimal	cash_excp_amt_lim;
	private BigDecimal	clg_excp_amt_lim;
	private BigDecimal	xfer_excp_amt_lim;
	private BigDecimal	cash_cr_excp_amt_lim;
	private BigDecimal	clg_cr_excp_amt_lim;
	private BigDecimal	xfer_cr_excp_amt_lim;
	private BigDecimal	cash_abnrml_amt_lim;
	private BigDecimal	clg_abnrml_amt_lim;
	private BigDecimal	xfer_abnrml_amt_lim;
	private BigDecimal	cum_dr_amt;
	private BigDecimal	cum_cr_amt;
	private BigDecimal	acrd_cr_amt;
	private Date	last_tran_date;
	private String	mode_of_oper_code;
	private String	pb_ps_code;
	private String	serv_chrg_coll_flg;
	private String	free_text;
	private String	acct_turnover_det_flg;
	private String	nom_available_flg;
	private String	acct_locn_code;
	private Date	last_purge_date;
	private BigDecimal	bal_on_purge_date;
	private String	int_paid_flg;
	private String	int_coll_flg;
	private Date	last_any_tran_date;
	private String	hashed_no;
	private String	lchg_user_id;
	private Date	lchg_time;
	private String	rcre_user_id;
	private Date	rcre_time;
	private String	limit_b2kid;
	private String	drwng_power_ind;
	private BigDecimal	drwng_power_pcnt;
	private String	micr_chq_chrg_coll_flg;
	private Date	last_turnover_date;
	private BigDecimal	notional_rate;
	private String	notional_rate_code;
	private BigDecimal	fx_clr_bal_amt;
	private BigDecimal	fx_bal_on_purge_date;
	private String	fd_ref_num;
	private BigDecimal	fx_cum_cr_amt;
	private BigDecimal	fx_cum_dr_amt;
	private String	crncy_code;
	private String	source_of_fund;
	private String	anw_non_cust_alwd_flg;
	private String	acct_crncy_code;
	private BigDecimal	lien_amt;
	private String	acct_classification_flg;
	private String	system_only_acct_flg;
	private String	single_tran_flg;
	private BigDecimal	utilised_amt;
	private String	inter_sol_access_flg;
	private String	purge_allowed_flg;
	private String	purge_text;
	private Date	min_value_date;
	private String	acct_mgr_user_id;
	private String	schm_type;
	private Date	last_frez_date;
	private Date	last_unfrez_date;
	private BigDecimal	bal_on_frez_date;
	private String	swift_allowed_flg;
	private BigDecimal	dacc_lim_pcnt;
	private BigDecimal	dacc_lim_abs;
	private String	chrg_level_code;
	private String	acct_cls_chrg_pend_verf;
	private String	partitioned_flg;
	private String	partitioned_type;
	

	public String getAcid() {
		return acid;
	}
	public void setAcid(String acid) {
		this.acid = acid;
	}
	public String getEntity_cre_flg() {
		return entity_cre_flg;
	}
	public void setEntity_cre_flg(String entity_cre_flg) {
		this.entity_cre_flg = entity_cre_flg;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getSol_id() {
		return sol_id;
	}
	public void setSol_id(String sol_id) {
		this.sol_id = sol_id;
	}
	public String getAcct_prefix() {
		return acct_prefix;
	}
	public void setAcct_prefix(String acct_prefix) {
		this.acct_prefix = acct_prefix;
	}
	public String getAcct_num() {
		return acct_num;
	}
	public void setAcct_num(String acct_num) {
		this.acct_num = acct_num;
	}
	public String getBacid() {
		return bacid;
	}
	public void setBacid(String bacid) {
		this.bacid = bacid;
	}
	public String getForacid() {
		return foracid;
	}
	public void setForacid(String foracid) {
		this.foracid = foracid;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getAcct_short_name() {
		return acct_short_name;
	}
	public void setAcct_short_name(String acct_short_name) {
		this.acct_short_name = acct_short_name;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getGl_sub_head_code() {
		return gl_sub_head_code;
	}
	public void setGl_sub_head_code(String gl_sub_head_code) {
		this.gl_sub_head_code = gl_sub_head_code;
	}
	public String getAcct_ownership() {
		return acct_ownership;
	}
	public void setAcct_ownership(String acct_ownership) {
		this.acct_ownership = acct_ownership;
	}
	public String getSchm_code() {
		return schm_code;
	}
	public void setSchm_code(String schm_code) {
		this.schm_code = schm_code;
	}
	public BigDecimal getDr_bal_lim() {
		return dr_bal_lim;
	}
	public void setDr_bal_lim(BigDecimal dr_bal_lim) {
		this.dr_bal_lim = dr_bal_lim;
	}
	public String getAcct_rpt_code() {
		return acct_rpt_code;
	}
	public void setAcct_rpt_code(String acct_rpt_code) {
		this.acct_rpt_code = acct_rpt_code;
	}
	public String getFrez_code() {
		return frez_code;
	}
	public void setFrez_code(String frez_code) {
		this.frez_code = frez_code;
	}
	public String getFrez_reason_code() {
		return frez_reason_code;
	}
	public void setFrez_reason_code(String frez_reason_code) {
		this.frez_reason_code = frez_reason_code;
	}
	public Date getAcct_opn_date() {
		return acct_opn_date;
	}
	public void setAcct_opn_date(Date acct_opn_date) {
		this.acct_opn_date = acct_opn_date;
	}
	public String getAcct_cls_flg() {
		return acct_cls_flg;
	}
	public void setAcct_cls_flg(String acct_cls_flg) {
		this.acct_cls_flg = acct_cls_flg;
	}
	public Date getAcct_cls_date() {
		return acct_cls_date;
	}
	public void setAcct_cls_date(Date acct_cls_date) {
		this.acct_cls_date = acct_cls_date;
	}
	public BigDecimal getClr_bal_amt() {
		return clr_bal_amt;
	}
	public void setClr_bal_amt(BigDecimal clr_bal_amt) {
		this.clr_bal_amt = clr_bal_amt;
	}
	public BigDecimal getTot_mod_times() {
		return tot_mod_times;
	}
	public void setTot_mod_times(BigDecimal tot_mod_times) {
		this.tot_mod_times = tot_mod_times;
	}
	public BigDecimal getLedg_num() {
		return ledg_num;
	}
	public void setLedg_num(BigDecimal ledg_num) {
		this.ledg_num = ledg_num;
	}
	public BigDecimal getUn_clr_bal_amt() {
		return un_clr_bal_amt;
	}
	public void setUn_clr_bal_amt(BigDecimal un_clr_bal_amt) {
		this.un_clr_bal_amt = un_clr_bal_amt;
	}
	public BigDecimal getDrwng_power() {
		return drwng_power;
	}
	public void setDrwng_power(BigDecimal drwng_power) {
		this.drwng_power = drwng_power;
	}
	public BigDecimal getSanct_lim() {
		return sanct_lim;
	}
	public void setSanct_lim(BigDecimal sanct_lim) {
		this.sanct_lim = sanct_lim;
	}
	public BigDecimal getAdhoc_lim() {
		return adhoc_lim;
	}
	public void setAdhoc_lim(BigDecimal adhoc_lim) {
		this.adhoc_lim = adhoc_lim;
	}
	public BigDecimal getEmer_advn() {
		return emer_advn;
	}
	public void setEmer_advn(BigDecimal emer_advn) {
		this.emer_advn = emer_advn;
	}
	public BigDecimal getDacc_lim() {
		return dacc_lim;
	}
	public void setDacc_lim(BigDecimal dacc_lim) {
		this.dacc_lim = dacc_lim;
	}
	public BigDecimal getSystem_reserved_amt() {
		return system_reserved_amt;
	}
	public void setSystem_reserved_amt(BigDecimal system_reserved_amt) {
		this.system_reserved_amt = system_reserved_amt;
	}
	public BigDecimal getSingle_tran_lim() {
		return single_tran_lim;
	}
	public void setSingle_tran_lim(BigDecimal single_tran_lim) {
		this.single_tran_lim = single_tran_lim;
	}
	public BigDecimal getClean_adhoc_lim() {
		return clean_adhoc_lim;
	}
	public void setClean_adhoc_lim(BigDecimal clean_adhoc_lim) {
		this.clean_adhoc_lim = clean_adhoc_lim;
	}
	public BigDecimal getClean_emer_advn() {
		return clean_emer_advn;
	}
	public void setClean_emer_advn(BigDecimal clean_emer_advn) {
		this.clean_emer_advn = clean_emer_advn;
	}
	public BigDecimal getClean_single_tran_lim() {
		return clean_single_tran_lim;
	}
	public void setClean_single_tran_lim(BigDecimal clean_single_tran_lim) {
		this.clean_single_tran_lim = clean_single_tran_lim;
	}
	public BigDecimal getSystem_gen_lim() {
		return system_gen_lim;
	}
	public void setSystem_gen_lim(BigDecimal system_gen_lim) {
		this.system_gen_lim = system_gen_lim;
	}
	public String getChq_alwd_flg() {
		return chq_alwd_flg;
	}
	public void setChq_alwd_flg(String chq_alwd_flg) {
		this.chq_alwd_flg = chq_alwd_flg;
	}
	public BigDecimal getCash_excp_amt_lim() {
		return cash_excp_amt_lim;
	}
	public void setCash_excp_amt_lim(BigDecimal cash_excp_amt_lim) {
		this.cash_excp_amt_lim = cash_excp_amt_lim;
	}
	public BigDecimal getClg_excp_amt_lim() {
		return clg_excp_amt_lim;
	}
	public void setClg_excp_amt_lim(BigDecimal clg_excp_amt_lim) {
		this.clg_excp_amt_lim = clg_excp_amt_lim;
	}
	public BigDecimal getXfer_excp_amt_lim() {
		return xfer_excp_amt_lim;
	}
	public void setXfer_excp_amt_lim(BigDecimal xfer_excp_amt_lim) {
		this.xfer_excp_amt_lim = xfer_excp_amt_lim;
	}
	public BigDecimal getCash_cr_excp_amt_lim() {
		return cash_cr_excp_amt_lim;
	}
	public void setCash_cr_excp_amt_lim(BigDecimal cash_cr_excp_amt_lim) {
		this.cash_cr_excp_amt_lim = cash_cr_excp_amt_lim;
	}
	public BigDecimal getClg_cr_excp_amt_lim() {
		return clg_cr_excp_amt_lim;
	}
	public void setClg_cr_excp_amt_lim(BigDecimal clg_cr_excp_amt_lim) {
		this.clg_cr_excp_amt_lim = clg_cr_excp_amt_lim;
	}
	public BigDecimal getXfer_cr_excp_amt_lim() {
		return xfer_cr_excp_amt_lim;
	}
	public void setXfer_cr_excp_amt_lim(BigDecimal xfer_cr_excp_amt_lim) {
		this.xfer_cr_excp_amt_lim = xfer_cr_excp_amt_lim;
	}
	public BigDecimal getCash_abnrml_amt_lim() {
		return cash_abnrml_amt_lim;
	}
	public void setCash_abnrml_amt_lim(BigDecimal cash_abnrml_amt_lim) {
		this.cash_abnrml_amt_lim = cash_abnrml_amt_lim;
	}
	public BigDecimal getClg_abnrml_amt_lim() {
		return clg_abnrml_amt_lim;
	}
	public void setClg_abnrml_amt_lim(BigDecimal clg_abnrml_amt_lim) {
		this.clg_abnrml_amt_lim = clg_abnrml_amt_lim;
	}
	public BigDecimal getXfer_abnrml_amt_lim() {
		return xfer_abnrml_amt_lim;
	}
	public void setXfer_abnrml_amt_lim(BigDecimal xfer_abnrml_amt_lim) {
		this.xfer_abnrml_amt_lim = xfer_abnrml_amt_lim;
	}
	public BigDecimal getCum_dr_amt() {
		return cum_dr_amt;
	}
	public void setCum_dr_amt(BigDecimal cum_dr_amt) {
		this.cum_dr_amt = cum_dr_amt;
	}
	public BigDecimal getCum_cr_amt() {
		return cum_cr_amt;
	}
	public void setCum_cr_amt(BigDecimal cum_cr_amt) {
		this.cum_cr_amt = cum_cr_amt;
	}
	public BigDecimal getAcrd_cr_amt() {
		return acrd_cr_amt;
	}
	public void setAcrd_cr_amt(BigDecimal acrd_cr_amt) {
		this.acrd_cr_amt = acrd_cr_amt;
	}
	public Date getLast_tran_date() {
		return last_tran_date;
	}
	public void setLast_tran_date(Date last_tran_date) {
		this.last_tran_date = last_tran_date;
	}
	public String getMode_of_oper_code() {
		return mode_of_oper_code;
	}
	public void setMode_of_oper_code(String mode_of_oper_code) {
		this.mode_of_oper_code = mode_of_oper_code;
	}
	public String getPb_ps_code() {
		return pb_ps_code;
	}
	public void setPb_ps_code(String pb_ps_code) {
		this.pb_ps_code = pb_ps_code;
	}
	public String getServ_chrg_coll_flg() {
		return serv_chrg_coll_flg;
	}
	public void setServ_chrg_coll_flg(String serv_chrg_coll_flg) {
		this.serv_chrg_coll_flg = serv_chrg_coll_flg;
	}
	public String getFree_text() {
		return free_text;
	}
	public void setFree_text(String free_text) {
		this.free_text = free_text;
	}
	public String getAcct_turnover_det_flg() {
		return acct_turnover_det_flg;
	}
	public void setAcct_turnover_det_flg(String acct_turnover_det_flg) {
		this.acct_turnover_det_flg = acct_turnover_det_flg;
	}
	public String getNom_available_flg() {
		return nom_available_flg;
	}
	public void setNom_available_flg(String nom_available_flg) {
		this.nom_available_flg = nom_available_flg;
	}
	public String getAcct_locn_code() {
		return acct_locn_code;
	}
	public void setAcct_locn_code(String acct_locn_code) {
		this.acct_locn_code = acct_locn_code;
	}
	public Date getLast_purge_date() {
		return last_purge_date;
	}
	public void setLast_purge_date(Date last_purge_date) {
		this.last_purge_date = last_purge_date;
	}
	public BigDecimal getBal_on_purge_date() {
		return bal_on_purge_date;
	}
	public void setBal_on_purge_date(BigDecimal bal_on_purge_date) {
		this.bal_on_purge_date = bal_on_purge_date;
	}
	public String getInt_paid_flg() {
		return int_paid_flg;
	}
	public void setInt_paid_flg(String int_paid_flg) {
		this.int_paid_flg = int_paid_flg;
	}
	public String getInt_coll_flg() {
		return int_coll_flg;
	}
	public void setInt_coll_flg(String int_coll_flg) {
		this.int_coll_flg = int_coll_flg;
	}
	public Date getLast_any_tran_date() {
		return last_any_tran_date;
	}
	public void setLast_any_tran_date(Date last_any_tran_date) {
		this.last_any_tran_date = last_any_tran_date;
	}
	public String getHashed_no() {
		return hashed_no;
	}
	public void setHashed_no(String hashed_no) {
		this.hashed_no = hashed_no;
	}
	public String getLchg_user_id() {
		return lchg_user_id;
	}
	public void setLchg_user_id(String lchg_user_id) {
		this.lchg_user_id = lchg_user_id;
	}
	public Date getLchg_time() {
		return lchg_time;
	}
	public void setLchg_time(Date lchg_time) {
		this.lchg_time = lchg_time;
	}
	public String getRcre_user_id() {
		return rcre_user_id;
	}
	public void setRcre_user_id(String rcre_user_id) {
		this.rcre_user_id = rcre_user_id;
	}
	public Date getRcre_time() {
		return rcre_time;
	}
	public void setRcre_time(Date rcre_time) {
		this.rcre_time = rcre_time;
	}
	public String getLimit_b2kid() {
		return limit_b2kid;
	}
	public void setLimit_b2kid(String limit_b2kid) {
		this.limit_b2kid = limit_b2kid;
	}
	public String getDrwng_power_ind() {
		return drwng_power_ind;
	}
	public void setDrwng_power_ind(String drwng_power_ind) {
		this.drwng_power_ind = drwng_power_ind;
	}
	public BigDecimal getDrwng_power_pcnt() {
		return drwng_power_pcnt;
	}
	public void setDrwng_power_pcnt(BigDecimal drwng_power_pcnt) {
		this.drwng_power_pcnt = drwng_power_pcnt;
	}
	public String getMicr_chq_chrg_coll_flg() {
		return micr_chq_chrg_coll_flg;
	}
	public void setMicr_chq_chrg_coll_flg(String micr_chq_chrg_coll_flg) {
		this.micr_chq_chrg_coll_flg = micr_chq_chrg_coll_flg;
	}
	public Date getLast_turnover_date() {
		return last_turnover_date;
	}
	public void setLast_turnover_date(Date last_turnover_date) {
		this.last_turnover_date = last_turnover_date;
	}
	public BigDecimal getNotional_rate() {
		return notional_rate;
	}
	public void setNotional_rate(BigDecimal notional_rate) {
		this.notional_rate = notional_rate;
	}
	public String getNotional_rate_code() {
		return notional_rate_code;
	}
	public void setNotional_rate_code(String notional_rate_code) {
		this.notional_rate_code = notional_rate_code;
	}
	public BigDecimal getFx_clr_bal_amt() {
		return fx_clr_bal_amt;
	}
	public void setFx_clr_bal_amt(BigDecimal fx_clr_bal_amt) {
		this.fx_clr_bal_amt = fx_clr_bal_amt;
	}
	public BigDecimal getFx_bal_on_purge_date() {
		return fx_bal_on_purge_date;
	}
	public void setFx_bal_on_purge_date(BigDecimal fx_bal_on_purge_date) {
		this.fx_bal_on_purge_date = fx_bal_on_purge_date;
	}
	public String getFd_ref_num() {
		return fd_ref_num;
	}
	public void setFd_ref_num(String fd_ref_num) {
		this.fd_ref_num = fd_ref_num;
	}
	public BigDecimal getFx_cum_cr_amt() {
		return fx_cum_cr_amt;
	}
	public void setFx_cum_cr_amt(BigDecimal fx_cum_cr_amt) {
		this.fx_cum_cr_amt = fx_cum_cr_amt;
	}
	public BigDecimal getFx_cum_dr_amt() {
		return fx_cum_dr_amt;
	}
	public void setFx_cum_dr_amt(BigDecimal fx_cum_dr_amt) {
		this.fx_cum_dr_amt = fx_cum_dr_amt;
	}
	public String getCrncy_code() {
		return crncy_code;
	}
	public void setCrncy_code(String crncy_code) {
		this.crncy_code = crncy_code;
	}
	public String getSource_of_fund() {
		return source_of_fund;
	}
	public void setSource_of_fund(String source_of_fund) {
		this.source_of_fund = source_of_fund;
	}
	public String getAnw_non_cust_alwd_flg() {
		return anw_non_cust_alwd_flg;
	}
	public void setAnw_non_cust_alwd_flg(String anw_non_cust_alwd_flg) {
		this.anw_non_cust_alwd_flg = anw_non_cust_alwd_flg;
	}
	public String getAcct_crncy_code() {
		return acct_crncy_code;
	}
	public void setAcct_crncy_code(String acct_crncy_code) {
		this.acct_crncy_code = acct_crncy_code;
	}
	public BigDecimal getLien_amt() {
		return lien_amt;
	}
	public void setLien_amt(BigDecimal lien_amt) {
		this.lien_amt = lien_amt;
	}
	public String getAcct_classification_flg() {
		return acct_classification_flg;
	}
	public void setAcct_classification_flg(String acct_classification_flg) {
		this.acct_classification_flg = acct_classification_flg;
	}
	public String getSystem_only_acct_flg() {
		return system_only_acct_flg;
	}
	public void setSystem_only_acct_flg(String system_only_acct_flg) {
		this.system_only_acct_flg = system_only_acct_flg;
	}
	public String getSingle_tran_flg() {
		return single_tran_flg;
	}
	public void setSingle_tran_flg(String single_tran_flg) {
		this.single_tran_flg = single_tran_flg;
	}
	public BigDecimal getUtilised_amt() {
		return utilised_amt;
	}
	public void setUtilised_amt(BigDecimal utilised_amt) {
		this.utilised_amt = utilised_amt;
	}
	public String getInter_sol_access_flg() {
		return inter_sol_access_flg;
	}
	public void setInter_sol_access_flg(String inter_sol_access_flg) {
		this.inter_sol_access_flg = inter_sol_access_flg;
	}
	public String getPurge_allowed_flg() {
		return purge_allowed_flg;
	}
	public void setPurge_allowed_flg(String purge_allowed_flg) {
		this.purge_allowed_flg = purge_allowed_flg;
	}
	public String getPurge_text() {
		return purge_text;
	}
	public void setPurge_text(String purge_text) {
		this.purge_text = purge_text;
	}
	public Date getMin_value_date() {
		return min_value_date;
	}
	public void setMin_value_date(Date min_value_date) {
		this.min_value_date = min_value_date;
	}
	public String getAcct_mgr_user_id() {
		return acct_mgr_user_id;
	}
	public void setAcct_mgr_user_id(String acct_mgr_user_id) {
		this.acct_mgr_user_id = acct_mgr_user_id;
	}
	public String getSchm_type() {
		return schm_type;
	}
	public void setSchm_type(String schm_type) {
		this.schm_type = schm_type;
	}
	public Date getLast_frez_date() {
		return last_frez_date;
	}
	public void setLast_frez_date(Date last_frez_date) {
		this.last_frez_date = last_frez_date;
	}
	public Date getLast_unfrez_date() {
		return last_unfrez_date;
	}
	public void setLast_unfrez_date(Date last_unfrez_date) {
		this.last_unfrez_date = last_unfrez_date;
	}
	public BigDecimal getBal_on_frez_date() {
		return bal_on_frez_date;
	}
	public void setBal_on_frez_date(BigDecimal bal_on_frez_date) {
		this.bal_on_frez_date = bal_on_frez_date;
	}
	public String getSwift_allowed_flg() {
		return swift_allowed_flg;
	}
	public void setSwift_allowed_flg(String swift_allowed_flg) {
		this.swift_allowed_flg = swift_allowed_flg;
	}
	public BigDecimal getDacc_lim_pcnt() {
		return dacc_lim_pcnt;
	}
	public void setDacc_lim_pcnt(BigDecimal dacc_lim_pcnt) {
		this.dacc_lim_pcnt = dacc_lim_pcnt;
	}
	public BigDecimal getDacc_lim_abs() {
		return dacc_lim_abs;
	}
	public void setDacc_lim_abs(BigDecimal dacc_lim_abs) {
		this.dacc_lim_abs = dacc_lim_abs;
	}
	public String getChrg_level_code() {
		return chrg_level_code;
	}
	public void setChrg_level_code(String chrg_level_code) {
		this.chrg_level_code = chrg_level_code;
	}
	public String getAcct_cls_chrg_pend_verf() {
		return acct_cls_chrg_pend_verf;
	}
	public void setAcct_cls_chrg_pend_verf(String acct_cls_chrg_pend_verf) {
		this.acct_cls_chrg_pend_verf = acct_cls_chrg_pend_verf;
	}
	public String getPartitioned_flg() {
		return partitioned_flg;
	}
	public void setPartitioned_flg(String partitioned_flg) {
		this.partitioned_flg = partitioned_flg;
	}
	public String getPartitioned_type() {
		return partitioned_type;
	}
	public void setPartitioned_type(String partitioned_type) {
		this.partitioned_type = partitioned_type;
	}

	public Account(String acct_num, String acct_name, String acct_short_name, String acid,
			BigDecimal cash_abnrml_amt_lim) {
		 
		this.acct_num=acct_num;
		this.acct_name=acct_name;
		this.acct_short_name=acct_short_name;
		this.acid=acid;
		this.cash_abnrml_amt_lim=cash_abnrml_amt_lim;
		
		
		
		
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Account(String acct_num, BigDecimal cash_abnrml_amt_lim) {
		
		this.acct_num=acct_num;
		this.cash_abnrml_amt_lim=cash_abnrml_amt_lim;

	}

}
