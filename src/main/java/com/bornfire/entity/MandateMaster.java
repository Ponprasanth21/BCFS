package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BIPS_MANDATE_MASTER")
@IdClass(MandateMasterDetailID.class)
public class MandateMaster implements Serializable{
	private String mandate_ref_no;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date mandate_lodge_date;
	private String lodged_by;
	private String mand_cust_ref_no;
	private String mand_uri_no;
	private String mand_cust_id;
	private String man_born_cust_id;
	private String mand_cust_name;
	private String mand_bank_code;
	private String mand_bank_name;
	private String mand_branch_code;
	private String mand_branch_name;
	private String mand_bank_swift_code;
	private String mand_branch_swift_bic_code;
	private String mand_account_type;
	@Id
	private String mand_account_no;
	private String mand_iban_account_no;
	private String mand_account_name;
	private String mand_nick_name;
	private String ben_cust_ref_no;
	private String ben_uri_no;
	private String ben_cust_id;
	private String ben_born_cust_id;
	private String ben_cust_name;
	private String ben_bank_code;
	private String ben_bank_name;
	private String ben_branch_code;
	private String ben_branch_name;
	private String ben_bank_swift_code;
	private String ben_branch_swift_bic_code;
	private String ben_account_type;
	@Id
	private String ben_account_no;
	private String ben_iban_account_no;
	private String ben_account_name;
	private String ben_nick_name;
	private BigDecimal auth_amount;
	private String one_time_flg;
	private String periodicity;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date start_date;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date end_date;
	private String entity_cre_flg;
	private String del_flg;
	private String modify_flg;
	private String entry_user;
	private String modify_user;
	private String auth_user;
	private Date entry_time;
	private Date modify_time;
	private Date auth_time;
	private String login_mode;
	private String channel_id;
	private String ip_addr;
	private String status_flg;
	private String restrict_flg;
	private String remarks;
	private String purpose;
	private BigDecimal min_amount;
	private BigDecimal max_amount;
	@Lob
	private byte[] mand_doc_image;
	private String record_status;
	private String registration_no;
	private String mand_type;
	private String services;
	private String mand_remarks;
	private String suspend_flg;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date suspend_date;
	private String lodger_remarks;
	
	public MandateMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMandate_ref_no() {
		return mandate_ref_no;
	}

	public void setMandate_ref_no(String mandate_ref_no) {
		this.mandate_ref_no = mandate_ref_no;
	}


	public Date getMandate_lodge_date() {
		return mandate_lodge_date;
	}

	public void setMandate_lodge_date(Date mandate_lodge_date) {
		this.mandate_lodge_date = mandate_lodge_date;
	}

	public String getLodged_by() {
		return lodged_by;
	}

	public void setLodged_by(String lodged_by) {
		this.lodged_by = lodged_by;
	}

	public String getMand_cust_ref_no() {
		return mand_cust_ref_no;
	}

	public void setMand_cust_ref_no(String mand_cust_ref_no) {
		this.mand_cust_ref_no = mand_cust_ref_no;
	}

	public String getMand_uri_no() {
		return mand_uri_no;
	}

	public void setMand_uri_no(String mand_uri_no) {
		this.mand_uri_no = mand_uri_no;
	}

	public String getMand_cust_id() {
		return mand_cust_id;
	}

	public void setMand_cust_id(String mand_cust_id) {
		this.mand_cust_id = mand_cust_id;
	}

	public String getMan_born_cust_id() {
		return man_born_cust_id;
	}

	public void setMan_born_cust_id(String man_born_cust_id) {
		this.man_born_cust_id = man_born_cust_id;
	}

	public String getMand_cust_name() {
		return mand_cust_name;
	}

	public void setMand_cust_name(String mand_cust_name) {
		this.mand_cust_name = mand_cust_name;
	}

	public String getMand_bank_code() {
		return mand_bank_code;
	}

	public void setMand_bank_code(String mand_bank_code) {
		this.mand_bank_code = mand_bank_code;
	}

	public String getMand_bank_name() {
		return mand_bank_name;
	}

	public void setMand_bank_name(String mand_bank_name) {
		this.mand_bank_name = mand_bank_name;
	}

	public String getMand_branch_code() {
		return mand_branch_code;
	}

	public void setMand_branch_code(String mand_branch_code) {
		this.mand_branch_code = mand_branch_code;
	}

	public String getMand_branch_name() {
		return mand_branch_name;
	}

	public void setMand_branch_name(String mand_branch_name) {
		this.mand_branch_name = mand_branch_name;
	}

	

	public String getMand_bank_swift_code() {
		return mand_bank_swift_code;
	}

	public void setMand_bank_swift_code(String mand_bank_swift_code) {
		this.mand_bank_swift_code = mand_bank_swift_code;
	}

	public String getMand_branch_swift_bic_code() {
		return mand_branch_swift_bic_code;
	}

	public void setMand_branch_swift_bic_code(String mand_branch_swift_bic_code) {
		this.mand_branch_swift_bic_code = mand_branch_swift_bic_code;
	}

	public String getMand_account_type() {
		return mand_account_type;
	}

	public void setMand_account_type(String mand_account_type) {
		this.mand_account_type = mand_account_type;
	}

	public String getMand_account_no() {
		return mand_account_no;
	}

	public void setMand_account_no(String mand_account_no) {
		this.mand_account_no = mand_account_no;
	}

	public String getMand_iban_account_no() {
		return mand_iban_account_no;
	}

	public void setMand_iban_account_no(String mand_iban_account_no) {
		this.mand_iban_account_no = mand_iban_account_no;
	}

	public String getMand_account_name() {
		return mand_account_name;
	}

	public void setMand_account_name(String mand_account_name) {
		this.mand_account_name = mand_account_name;
	}

	public String getMand_nick_name() {
		return mand_nick_name;
	}

	public void setMand_nick_name(String mand_nick_name) {
		this.mand_nick_name = mand_nick_name;
	}

	public String getBen_cust_ref_no() {
		return ben_cust_ref_no;
	}

	public void setBen_cust_ref_no(String ben_cust_ref_no) {
		this.ben_cust_ref_no = ben_cust_ref_no;
	}

	public String getBen_uri_no() {
		return ben_uri_no;
	}

	public void setBen_uri_no(String ben_uri_no) {
		this.ben_uri_no = ben_uri_no;
	}

	public String getBen_cust_id() {
		return ben_cust_id;
	}

	public void setBen_cust_id(String ben_cust_id) {
		this.ben_cust_id = ben_cust_id;
	}

	public String getBen_born_cust_id() {
		return ben_born_cust_id;
	}

	public void setBen_born_cust_id(String ben_born_cust_id) {
		this.ben_born_cust_id = ben_born_cust_id;
	}

	public String getBen_cust_name() {
		return ben_cust_name;
	}

	public void setBen_cust_name(String ben_cust_name) {
		this.ben_cust_name = ben_cust_name;
	}

	public String getBen_bank_code() {
		return ben_bank_code;
	}

	public void setBen_bank_code(String ben_bank_code) {
		this.ben_bank_code = ben_bank_code;
	}

	public String getBen_bank_name() {
		return ben_bank_name;
	}

	public void setBen_bank_name(String ben_bank_name) {
		this.ben_bank_name = ben_bank_name;
	}

	public String getBen_branch_code() {
		return ben_branch_code;
	}

	public void setBen_branch_code(String ben_branch_code) {
		this.ben_branch_code = ben_branch_code;
	}

	public String getBen_branch_name() {
		return ben_branch_name;
	}

	public void setBen_branch_name(String ben_branch_name) {
		this.ben_branch_name = ben_branch_name;
	}

	public String getBen_bank_swift_code() {
		return ben_bank_swift_code;
	}

	public void setBen_bank_swift_code(String ben_bank_swift_code) {
		this.ben_bank_swift_code = ben_bank_swift_code;
	}

	public String getBen_branch_swift_bic_code() {
		return ben_branch_swift_bic_code;
	}

	public void setBen_branch_swift_bic_code(String ben_branch_swift_bic_code) {
		this.ben_branch_swift_bic_code = ben_branch_swift_bic_code;
	}

	public String getBen_account_type() {
		return ben_account_type;
	}

	public void setBen_account_type(String ben_account_type) {
		this.ben_account_type = ben_account_type;
	}

	public String getBen_account_no() {
		return ben_account_no;
	}

	public void setBen_account_no(String ben_account_no) {
		this.ben_account_no = ben_account_no;
	}

	public String getBen_iban_account_no() {
		return ben_iban_account_no;
	}

	public void setBen_iban_account_no(String ben_iban_account_no) {
		this.ben_iban_account_no = ben_iban_account_no;
	}

	public String getBen_account_name() {
		return ben_account_name;
	}

	public void setBen_account_name(String ben_account_name) {
		this.ben_account_name = ben_account_name;
	}

	public String getBen_nick_name() {
		return ben_nick_name;
	}

	public void setBen_nick_name(String ben_nick_name) {
		this.ben_nick_name = ben_nick_name;
	}

	public BigDecimal getAuth_amount() {
		return auth_amount;
	}

	public void setAuth_amount(BigDecimal auth_amount) {
		this.auth_amount = auth_amount;
	}

	public String getOne_time_flg() {
		return one_time_flg;
	}

	public void setOne_time_flg(String one_time_flg) {
		this.one_time_flg = one_time_flg;
	}

	public String getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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

	public String getModify_flg() {
		return modify_flg;
	}

	public void setModify_flg(String modify_flg) {
		this.modify_flg = modify_flg;
	}

	public String getEntry_user() {
		return entry_user;
	}

	public void setEntry_user(String entry_user) {
		this.entry_user = entry_user;
	}

	public String getModify_user() {
		return modify_user;
	}

	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}

	public String getAuth_user() {
		return auth_user;
	}

	public void setAuth_user(String auth_user) {
		this.auth_user = auth_user;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public Date getAuth_time() {
		return auth_time;
	}

	public void setAuth_time(Date auth_time) {
		this.auth_time = auth_time;
	}

	public String getLogin_mode() {
		return login_mode;
	}

	public void setLogin_mode(String login_mode) {
		this.login_mode = login_mode;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getIp_addr() {
		return ip_addr;
	}

	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}

	public String getStatus_flg() {
		return status_flg;
	}

	public void setStatus_flg(String status_flg) {
		this.status_flg = status_flg;
	}

	public String getRestrict_flg() {
		return restrict_flg;
	}

	public void setRestrict_flg(String restrict_flg) {
		this.restrict_flg = restrict_flg;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	
	

	public BigDecimal getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(BigDecimal min_amount) {
		this.min_amount = min_amount;
	}

	public BigDecimal getMax_amount() {
		return max_amount;
	}

	public void setMax_amount(BigDecimal max_amount) {
		this.max_amount = max_amount;
	}

	public byte[] getMand_doc_image() {
		return mand_doc_image;
	}

	public void setMand_doc_image(byte[] mand_doc_image) {
		this.mand_doc_image = mand_doc_image;
	}

	
	public String getRecord_status() {
		return record_status;
	}

	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	
	

	public String getRegistration_no() {
		return registration_no;
	}

	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}

	public String getMand_type() {
		return mand_type;
	}

	public void setMand_type(String mand_type) {
		this.mand_type = mand_type;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getMand_remarks() {
		return mand_remarks;
	}

	public void setMand_remarks(String mand_remarks) {
		this.mand_remarks = mand_remarks;
	}

	public String getSuspend_flg() {
		return suspend_flg;
	}

	public void setSuspend_flg(String suspend_flg) {
		this.suspend_flg = suspend_flg;
	}

	public Date getSuspend_date() {
		return suspend_date;
	}

	public void setSuspend_date(Date suspend_date) {
		this.suspend_date = suspend_date;
	}

	public String getLodger_remarks() {
		return lodger_remarks;
	}

	public void setLodger_remarks(String lodger_remarks) {
		this.lodger_remarks = lodger_remarks;
	}

	@Override
	public String toString() {
		return "MandateMaster [mandate_ref_no=" + mandate_ref_no + ", mandate_lodge_date=" + mandate_lodge_date
				+ ", lodged_by=" + lodged_by + ", mand_cust_ref_no=" + mand_cust_ref_no + ", mand_uri_no=" + mand_uri_no
				+ ", mand_cust_id=" + mand_cust_id + ", man_born_cust_id=" + man_born_cust_id + ", mand_cust_name="
				+ mand_cust_name + ", mand_bank_code=" + mand_bank_code + ", mand_bank_name=" + mand_bank_name
				+ ", mand_branch_code=" + mand_branch_code + ", mand_branch_name=" + mand_branch_name
				+ ", mand_bank_swift_code=" + mand_bank_swift_code + ", mand_branch_swift_bic_code="
				+ mand_branch_swift_bic_code + ", mand_account_type=" + mand_account_type + ", mand_account_no="
				+ mand_account_no + ", mand_iban_account_no=" + mand_iban_account_no + ", mand_account_name="
				+ mand_account_name + ", mand_nick_name=" + mand_nick_name + ", ben_cust_ref_no=" + ben_cust_ref_no
				+ ", ben_uri_no=" + ben_uri_no + ", ben_cust_id=" + ben_cust_id + ", ben_born_cust_id="
				+ ben_born_cust_id + ", ben_cust_name=" + ben_cust_name + ", ben_bank_code=" + ben_bank_code
				+ ", ben_bank_name=" + ben_bank_name + ", ben_branch_code=" + ben_branch_code + ", ben_branch_name="
				+ ben_branch_name + ", ben_bank_swift_code=" + ben_bank_swift_code + ", ben_branch_swift_bic_code="
				+ ben_branch_swift_bic_code + ", ben_account_type=" + ben_account_type + ", ben_account_no="
				+ ben_account_no + ", ben_iban_account_no=" + ben_iban_account_no + ", ben_account_name="
				+ ben_account_name + ", ben_nick_name=" + ben_nick_name + ", auth_amount=" + auth_amount
				+ ", one_time_flg=" + one_time_flg + ", periodicity=" + periodicity + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", entity_cre_flg=" + entity_cre_flg + ", del_flg=" + del_flg
				+ ", modify_flg=" + modify_flg + ", entry_user=" + entry_user + ", modify_user=" + modify_user
				+ ", auth_user=" + auth_user + ", entry_time=" + entry_time + ", modify_time=" + modify_time
				+ ", auth_time=" + auth_time + ", login_mode=" + login_mode + ", channel_id=" + channel_id
				+ ", ip_addr=" + ip_addr + ", status_flg=" + status_flg + ", restrict_flg=" + restrict_flg
				+ ", remarks=" + remarks + ", purpose=" + purpose + ", min_amount=" + min_amount + ", max_amount="
				+ max_amount + ", mand_doc_image=" + Arrays.toString(mand_doc_image) + ", record_status="
				+ record_status + ", registration_no=" + registration_no + ", mand_type=" + mand_type + ", services="
				+ services + ", mand_remarks=" + mand_remarks + ", suspend_flg=" + suspend_flg + ", suspend_date="
				+ suspend_date + ", lodger_remarks=" + lodger_remarks + "]";
	}

	


	
	

	
	
	
	
}
