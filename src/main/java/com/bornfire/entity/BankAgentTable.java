package com.bornfire.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BIPS_OTHER_BANK_AGENT_TABLE")
public class BankAgentTable {
	@Id
	private String bank_code;
	private String bank_name;
	
	private String bank_agent;
	private String bank_agent_account;
	private String entry_user;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date entry_time;
	private String modify_user;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date modify_time;
	private String del_user;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date del_time;
	private String del_flg;
	private String entity_cre_flg;
	private String modify_flg;
	private String entity_flg;
	private String address;
	private String verify_user;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date verify_time;
	private String parking_acct_num;
	
	private String disable_flg;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date disable_time;
	private String disable_user;
	private String new_bank_flg;

	public BankAgentTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_agent() {
		return bank_agent;
	}

	public void setBank_agent(String bank_agent) {
		this.bank_agent = bank_agent;
	}

	public String getBank_agent_account() {
		return bank_agent_account;
	}

	public void setBank_agent_account(String bank_agent_account) {
		this.bank_agent_account = bank_agent_account;
	}

	public String getEntry_user() {
		return entry_user;
	}

	public void setEntry_user(String entry_user) {
		this.entry_user = entry_user;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public String getModify_user() {
		return modify_user;
	}

	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public String getDel_user() {
		return del_user;
	}

	public void setDel_user(String del_user) {
		this.del_user = del_user;
	}

	public Date getDel_time() {
		return del_time;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}

	public String getEntity_cre_flg() {
		return entity_cre_flg;
	}

	public void setEntity_cre_flg(String entity_cre_flg) {
		this.entity_cre_flg = entity_cre_flg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getModify_flg() {
		return modify_flg;
	}

	public String getEntity_flg() {
		return entity_flg;
	}

	public String getVerify_user() {
		return verify_user;
	}

	public void setModify_flg(String modify_flg) {
		this.modify_flg = modify_flg;
	}

	public void setEntity_flg(String entity_flg) {
		this.entity_flg = entity_flg;
	}

	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}

	public Date getVerify_time() {
		return verify_time;
	}

	public void setVerify_time(Date verify_time) {
		this.verify_time = verify_time;
	}
	
	

	public String getParking_acct_num() {
		return parking_acct_num;
	}

	public void setParking_acct_num(String parking_acct_num) {
		this.parking_acct_num = parking_acct_num;
	}

	public String getNew_bank_flg() {
		return new_bank_flg;
	}

	public void setNew_bank_flg(String new_bank_flg) {
		this.new_bank_flg = new_bank_flg;
	}

	public BankAgentTable(String bank_code, String bank_name, String bank_agent, String bank_agent_account,
			String entry_user, Date entry_time, String modify_user, Date modify_time, String del_user, Date del_time,
			String del_flg, String entity_cre_flg, String modify_flg, String entity_flg, String address,
			String verify_user, Date verify_time) {
		super();
		this.bank_code = bank_code;
		this.bank_name = bank_name;
		this.bank_agent = bank_agent;
		this.bank_agent_account = bank_agent_account;
		this.entry_user = entry_user;
		this.entry_time = entry_time;
		this.modify_user = modify_user;
		this.modify_time = modify_time;
		this.del_user = del_user;
		this.del_time = del_time;
		this.del_flg = del_flg;
		this.entity_cre_flg = entity_cre_flg;
		this.modify_flg = modify_flg;
		this.entity_flg = entity_flg;
		this.address = address;
		this.verify_user = verify_user;
		this.verify_time = verify_time;
	}

	public String getDisable_flg() {
		return disable_flg;
	}

	public void setDisable_flg(String disable_flg) {
		this.disable_flg = disable_flg;
	}

	public Date getDisable_time() {
		return disable_time;
	}

	public void setDisable_time(Date disable_time) {
		this.disable_time = disable_time;
	}

	public String getDisable_user() {
		return disable_user;
	}

	public void setDisable_user(String disable_user) {
		this.disable_user = disable_user;
	}
	
	public BankAgentTable(BankAgentTmpTable tmpData) {
		this.bank_code = tmpData.getBank_code();
		this.bank_name = tmpData.getBank_name();
		this.bank_agent = tmpData.getBank_agent();
		this.bank_agent_account = tmpData.getBank_agent_account();
		this.entry_user = tmpData.getEntry_user();
		this.entry_time = tmpData.getEntry_time();
		this.modify_user = tmpData.getModify_user();
		this.modify_time = tmpData.getModify_time();
		this.del_user = tmpData.getDel_user();
		this.del_time = tmpData.getDel_time();
		this.del_flg = tmpData.getDel_flg();
		this.entity_cre_flg = tmpData.getEntity_cre_flg();
		this.modify_flg = tmpData.getModify_flg();
		this.entity_flg = tmpData.getEntity_flg();
		this.address = tmpData.getAddress();
		this.verify_user = tmpData.getVerify_user();
		this.verify_time = tmpData.getVerify_time();
		this.disable_user = tmpData.getDisable_user();
		this.disable_time = tmpData.getDisable_time();
		this.disable_flg = tmpData.getDisable_flg();
		this.parking_acct_num = tmpData.getParking_acct_num();
		this.new_bank_flg = tmpData.getNew_bank_flg();
	}

}
