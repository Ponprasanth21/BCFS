package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="BIPS_MERCHANT_TABLE")
public class Merchant {
	private String merchant_code;
	private String merchant_trade_name;
	
	private BigDecimal registration_no;
	private String qr_code_ref;
	@Id
	private BigDecimal account_no;
	
	private String account_name;
	private String remarks;
	private String line_of_business;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zip_code;
	private BigDecimal phone;
	private BigDecimal mobile;
	private String website;
	private String mail_id;
	private String account_type;
	private String contact_person;
	private String qr_code;
	private String merchant_facilities;
	private String pos_reference;
	private String daily_limit_flg;
	private String daily_limit;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date start_date;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date end_date;
	private String fees;
	private String fees_type;
	private String fixed;
	private String variable;
	private String mode1;
	private String commision;
	private String commision_type;
	private String special_notes;
    private String entry_user;
    private Date entry_time;
    private String modify_user;
    private Date modify_time;
    private String verify_user;
    private Date verify_time;
	private String entity_flg;
	
	private String modify_flg;
	private String del_flg;
	private BigDecimal account_no2;
	
	private String bank_name;
	private BigDecimal contact_person_mobile;
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getMerchant_trade_name() {
		return merchant_trade_name;
	}
	public void setMerchant_trade_name(String merchant_trade_name) {
		this.merchant_trade_name = merchant_trade_name;
	}
	public BigDecimal getRegistration_no() {
		return registration_no;
	}
	public void setRegistration_no(BigDecimal registration_no) {
		this.registration_no = registration_no;
	}
	public String getQr_code_ref() {
		return qr_code_ref;
	}
	public void setQr_code_ref(String qr_code_ref) {
		this.qr_code_ref = qr_code_ref;
	}
	public BigDecimal getAccount_no() {
		return account_no;
	}
	public void setAccount_no(BigDecimal account_no) {
		this.account_no = account_no;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getLine_of_business() {
		return line_of_business;
	}
	public void setLine_of_business(String line_of_business) {
		this.line_of_business = line_of_business;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public BigDecimal getPhone() {
		return phone;
	}
	public void setPhone(BigDecimal phone) {
		this.phone = phone;
	}
	public BigDecimal getMobile() {
		return mobile;
	}
	public void setMobile(BigDecimal mobile) {
		this.mobile = mobile;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getMail_id() {
		return mail_id;
	}
	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getQr_code() {
		return qr_code;
	}
	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}
	public String getMerchant_facilities() {
		return merchant_facilities;
	}
	public void setMerchant_facilities(String merchant_facilities) {
		this.merchant_facilities = merchant_facilities;
	}
	public String getPos_reference() {
		return pos_reference;
	}
	public void setPos_reference(String pos_reference) {
		this.pos_reference = pos_reference;
	}
	public String getDaily_limit_flg() {
		return daily_limit_flg;
	}
	public void setDaily_limit_flg(String daily_limit_flg) {
		this.daily_limit_flg = daily_limit_flg;
	}
	public String getDaily_limit() {
		return daily_limit;
	}
	public void setDaily_limit(String daily_limit) {
		this.daily_limit = daily_limit;
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
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getFees_type() {
		return fees_type;
	}
	public void setFees_type(String fees_type) {
		this.fees_type = fees_type;
	}
	public String getFixed() {
		return fixed;
	}
	public void setFixed(String fixed) {
		this.fixed = fixed;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getMode1() {
		return mode1;
	}
	public void setMode1(String mode1) {
		this.mode1 = mode1;
	}
	
	
	public BigDecimal getAccount_no2() {
		return account_no2;
	}
	public void setAccount_no2(BigDecimal account_no2) {
		this.account_no2 = account_no2;
	}
	public String getCommision() {
		return commision;
	}
	public void setCommision(String commision) {
		this.commision = commision;
	}
	public String getCommision_type() {
		return commision_type;
	}
	public void setCommision_type(String commision_type) {
		this.commision_type = commision_type;
	}
	public String getSpecial_notes() {
		return special_notes;
	}
	public void setSpecial_notes(String special_notes) {
		this.special_notes = special_notes;
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
	public String getVerify_user() {
		return verify_user;
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
	public String getEntity_flg() {
		return entity_flg;
	}
	public void setEntity_flg(String entity_flg) {
		this.entity_flg = entity_flg;
	}
	public String getModify_flg() {
		return modify_flg;
	}
	public void setModify_flg(String modify_flg) {
		this.modify_flg = modify_flg;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	
	
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	
	
	public BigDecimal getContact_person_mobile() {
		return contact_person_mobile;
	}
	public void setContact_person_mobile(BigDecimal contact_person_mobile) {
		this.contact_person_mobile = contact_person_mobile;
	}
	@Override
	public String toString() {
		return "Merchant [merchant_code=" + merchant_code + ", merchant_trade_name=" + merchant_trade_name
				+ ", registration_no=" + registration_no + ", qr_code_ref=" + qr_code_ref + ", account_no=" + account_no
				+ ", account_name=" + account_name + ", remarks=" + remarks 
				+ ", line_of_business=" + line_of_business + ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", zip_code=" + zip_code + ", phone="
				+ phone + ", mobile=" + mobile + ", website=" + website + ", mail_id=" + mail_id + ", account_type="
				+ account_type + ", contact_person=" + contact_person + ", qr_code=" + qr_code
				+ ", merchant_facilities=" + merchant_facilities + ", pos_reference=" + pos_reference
				+ ", daily_limit_flg=" + daily_limit_flg + ", daily_limit=" + daily_limit + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", fees=" + fees + ", fees_type=" + fees_type + ", fixed=" + fixed
				+ ", variable=" + variable + ", mode1=" + mode1 + ", commision=" + commision + ", commision_type="
				+ commision_type + ", special_notes=" + special_notes + ", entry_user=" + entry_user + ", entry_time="
				+ entry_time + ", modify_user=" + modify_user + ", modify_time=" + modify_time + ", verify_user="
				+ verify_user + ", verify_time=" + verify_time + ", entity_flg=" + entity_flg + ", modify_flg="
				+ modify_flg + ", del_flg=" + del_flg + "]";
	}
	
	
    }
