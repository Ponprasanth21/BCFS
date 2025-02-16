package com.bornfire.entity;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BSWIFT_PARAMETER_TABLE")
public class Bswift_Parameter_Entity {
	@Id
	private String	srl_num;
	private String	rowno;
	private String	attribute;
	private String	form_mt_103_field_name;
	private String	form_mt_103_field;
	private String	form_mt_103_value;
	private String	form_mt_103_additional_details;
	private String	form_pacs_0008_description;
	private String	form_pacs_0008_field;
	private String	form_pacs_0008_value;
	private String	form_pacs_0008_additional_details;
	private String	form_pacs_0008_attribute;
	private String	entry_user;
	private Date	entry_time;
	private String	modify_user;
	private Date	modify_time;
	private String	verify_user;
	private Date	verify_time;
	private String	entity_flg;
	private String	auth_flg;
	private String	modify_flg;
	private String	del_flg;
	private String	verify_flg;
	private String	from_form;
	private String	to_form;
	private String	status;
	
	
	public String getFrom_form() {
		return from_form;
	}
	public void setFrom_form(String from_form) {
		this.from_form = from_form;
	}
	public String getTo_form() {
		return to_form;
	}
	public void setTo_form(String to_form) {
		this.to_form = to_form;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSrl_num() {
		return srl_num;
	}
	public void setSrl_num(String srl_num) {
		this.srl_num = srl_num;
	}
	public String getRowno() {
		return rowno;
	}
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getForm_mt_103_field_name() {
		return form_mt_103_field_name;
	}
	public void setForm_mt_103_field_name(String form_mt_103_field_name) {
		this.form_mt_103_field_name = form_mt_103_field_name;
	}
	public String getForm_mt_103_field() {
		return form_mt_103_field;
	}
	public void setForm_mt_103_field(String form_mt_103_field) {
		this.form_mt_103_field = form_mt_103_field;
	}
	public String getForm_mt_103_value() {
		return form_mt_103_value;
	}
	public void setForm_mt_103_value(String form_mt_103_value) {
		this.form_mt_103_value = form_mt_103_value;
	}
	public String getForm_mt_103_additional_details() {
		return form_mt_103_additional_details;
	}
	public void setForm_mt_103_additional_details(String form_mt_103_additional_details) {
		this.form_mt_103_additional_details = form_mt_103_additional_details;
	}
	public String getForm_pacs_0008_description() {
		return form_pacs_0008_description;
	}
	public void setForm_pacs_0008_description(String form_pacs_0008_description) {
		this.form_pacs_0008_description = form_pacs_0008_description;
	}
	public String getForm_pacs_0008_field() {
		return form_pacs_0008_field;
	}
	public void setForm_pacs_0008_field(String form_pacs_0008_field) {
		this.form_pacs_0008_field = form_pacs_0008_field;
	}
	public String getForm_pacs_0008_value() {
		return form_pacs_0008_value;
	}
	public void setForm_pacs_0008_value(String form_pacs_0008_value) {
		this.form_pacs_0008_value = form_pacs_0008_value;
	}
	public String getForm_pacs_0008_additional_details() {
		return form_pacs_0008_additional_details;
	}
	public void setForm_pacs_0008_additional_details(String form_pacs_0008_additional_details) {
		this.form_pacs_0008_additional_details = form_pacs_0008_additional_details;
	}
	public String getForm_pacs_0008_attribute() {
		return form_pacs_0008_attribute;
	}
	public void setForm_pacs_0008_attribute(String form_pacs_0008_attribute) {
		this.form_pacs_0008_attribute = form_pacs_0008_attribute;
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
	public String getAuth_flg() {
		return auth_flg;
	}
	public void setAuth_flg(String auth_flg) {
		this.auth_flg = auth_flg;
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
	public String getVerify_flg() {
		return verify_flg;
	}
	public void setVerify_flg(String verify_flg) {
		this.verify_flg = verify_flg;
	}
	 
	public Bswift_Parameter_Entity(String srl_num, String rowno, String attribute, String form_mt_103_field_name,
			String form_mt_103_field, String form_mt_103_value, String form_mt_103_additional_details,
			String form_pacs_0008_description, String form_pacs_0008_field, String form_pacs_0008_value,
			String form_pacs_0008_additional_details, String form_pacs_0008_attribute, String entry_user,
			Date entry_time, String modify_user, Date modify_time, String verify_user, Date verify_time,
			String entity_flg, String auth_flg, String modify_flg, String del_flg, String verify_flg, String from_form,
			String to_form, String status) {
		super();
		this.srl_num = srl_num;
		this.rowno = rowno;
		this.attribute = attribute;
		this.form_mt_103_field_name = form_mt_103_field_name;
		this.form_mt_103_field = form_mt_103_field;
		this.form_mt_103_value = form_mt_103_value;
		this.form_mt_103_additional_details = form_mt_103_additional_details;
		this.form_pacs_0008_description = form_pacs_0008_description;
		this.form_pacs_0008_field = form_pacs_0008_field;
		this.form_pacs_0008_value = form_pacs_0008_value;
		this.form_pacs_0008_additional_details = form_pacs_0008_additional_details;
		this.form_pacs_0008_attribute = form_pacs_0008_attribute;
		this.entry_user = entry_user;
		this.entry_time = entry_time;
		this.modify_user = modify_user;
		this.modify_time = modify_time;
		this.verify_user = verify_user;
		this.verify_time = verify_time;
		this.entity_flg = entity_flg;
		this.auth_flg = auth_flg;
		this.modify_flg = modify_flg;
		this.del_flg = del_flg;
		this.verify_flg = verify_flg;
		this.from_form = from_form;
		this.to_form = to_form;
		this.status = status;
	}
	public Bswift_Parameter_Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
