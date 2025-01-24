package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
@Entity
@Table(name="BIPS_SWIFT_MT_MSG_TABLE")
@Component
public class BIPS_SWIFT_MT_MSG implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String	srl_no;
	
	private String	file_mgt_ref_no;
	private String	sender_reference;
	private String	msg_type;
	@Lob
	private String	mt_message_file;
	private String	remitant_name;
	private String	remitant_account;
	private String	beneficairy_name;
	private String	beneficairy_account;
	private String	trnsaction_amount;
	private String	currency;
	private String	total_transaction_amount;
	private String	debitor_name;
	private String	debitor_agent;
	private String	creditor_name;
	private String	creditor_agent;
	private String	remittance_address;
	private String	beneficiary_address;
	private String	bank_operation_code;
	private String	creditor_account;
	private String	debitor_account;
	private String	msg_id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	date_of_process;

	public String getSrl_no() {
		return srl_no;
	}

	public void setSrl_no(String srl_no) {
		this.srl_no = srl_no;
	}

	public String getFile_mgt_ref_no() {
		return file_mgt_ref_no;
	}

	public void setFile_mgt_ref_no(String file_mgt_ref_no) {
		this.file_mgt_ref_no = file_mgt_ref_no;
	}

	public String getSender_reference() {
		return sender_reference;
	}

	public void setSender_reference(String sender_reference) {
		this.sender_reference = sender_reference;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getMt_message_file() {
		return mt_message_file;
	}

	public void setMt_message_file(String mt_message_file) {
		this.mt_message_file = mt_message_file;
	}

	public String getRemitant_name() {
		return remitant_name;
	}

	public void setRemitant_name(String remitant_name) {
		this.remitant_name = remitant_name;
	}

	public String getRemitant_account() {
		return remitant_account;
	}

	public void setRemitant_account(String remitant_account) {
		this.remitant_account = remitant_account;
	}

	public String getBeneficairy_name() {
		return beneficairy_name;
	}

	public void setBeneficairy_name(String beneficairy_name) {
		this.beneficairy_name = beneficairy_name;
	}

	public String getBeneficairy_account() {
		return beneficairy_account;
	}

	public void setBeneficairy_account(String beneficairy_account) {
		this.beneficairy_account = beneficairy_account;
	}

	public String getTrnsaction_amount() {
		return trnsaction_amount;
	}

	public void setTrnsaction_amount(String trnsaction_amount) {
		this.trnsaction_amount = trnsaction_amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTotal_transaction_amount() {
		return total_transaction_amount;
	}

	public void setTotal_transaction_amount(String total_transaction_amount) {
		this.total_transaction_amount = total_transaction_amount;
	}

	public String getDebitor_name() {
		return debitor_name;
	}

	public void setDebitor_name(String debitor_name) {
		this.debitor_name = debitor_name;
	}

	public String getDebitor_agent() {
		return debitor_agent;
	}

	public void setDebitor_agent(String debitor_agent) {
		this.debitor_agent = debitor_agent;
	}

	public String getCreditor_name() {
		return creditor_name;
	}

	public void setCreditor_name(String creditor_name) {
		this.creditor_name = creditor_name;
	}

	public String getCreditor_agent() {
		return creditor_agent;
	}

	public void setCreditor_agent(String creditor_agent) {
		this.creditor_agent = creditor_agent;
	}

	public String getRemittance_address() {
		return remittance_address;
	}

	public void setRemittance_address(String remittance_address) {
		this.remittance_address = remittance_address;
	}

	public String getBeneficiary_address() {
		return beneficiary_address;
	}

	public void setBeneficiary_address(String beneficiary_address) {
		this.beneficiary_address = beneficiary_address;
	}

	public String getBank_operation_code() {
		return bank_operation_code;
	}

	public void setBank_operation_code(String bank_operation_code) {
		this.bank_operation_code = bank_operation_code;
	}

	public String getCreditor_account() {
		return creditor_account;
	}

	public void setCreditor_account(String creditor_account) {
		this.creditor_account = creditor_account;
	}

	public String getDebitor_account() {
		return debitor_account;
	}

	public void setDebitor_account(String debitor_account) {
		this.debitor_account = debitor_account;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public Date getDate_of_process() {
		return date_of_process;
	}

	public void setDate_of_process(Date date_of_process) {
		this.date_of_process = date_of_process;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BIPS_SWIFT_MT_MSG(String srl_no, String file_mgt_ref_no, String sender_reference, String msg_type,
			String mt_message_file, String remitant_name, String remitant_account, String beneficairy_name,
			String beneficairy_account, String trnsaction_amount, String currency, String total_transaction_amount,
			String debitor_name, String debitor_agent, String creditor_name, String creditor_agent,
			String remittance_address, String beneficiary_address, String bank_operation_code, String creditor_account,
			String debitor_account, String msg_id, Date date_of_process) {
		super();
		this.srl_no = srl_no;
		this.file_mgt_ref_no = file_mgt_ref_no;
		this.sender_reference = sender_reference;
		this.msg_type = msg_type;
		this.mt_message_file = mt_message_file;
		this.remitant_name = remitant_name;
		this.remitant_account = remitant_account;
		this.beneficairy_name = beneficairy_name;
		this.beneficairy_account = beneficairy_account;
		this.trnsaction_amount = trnsaction_amount;
		this.currency = currency;
		this.total_transaction_amount = total_transaction_amount;
		this.debitor_name = debitor_name;
		this.debitor_agent = debitor_agent;
		this.creditor_name = creditor_name;
		this.creditor_agent = creditor_agent;
		this.remittance_address = remittance_address;
		this.beneficiary_address = beneficiary_address;
		this.bank_operation_code = bank_operation_code;
		this.creditor_account = creditor_account;
		this.debitor_account = debitor_account;
		this.msg_id = msg_id;
		this.date_of_process = date_of_process;
	}

	public BIPS_SWIFT_MT_MSG() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
