package com.bornfire.entity;
import java.io.Serializable;
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
@Table(name="FORM_TRANSFER")
@Component
public class FORM_TRANSFER_ENTITY {
	@Id
	private String	srl_num;
	private String	from_form;
	private String	to_form;
	private String	Status;
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
	public String getSrl_num() {
		return srl_num;
	}
	public void setSrl_num(String srl_num) {
		this.srl_num = srl_num;
	}
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
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
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
	public FORM_TRANSFER_ENTITY(String srl_num, String from_form, String to_form, String status, String entry_user,
			Date entry_time, String modify_user, Date modify_time, String verify_user, Date verify_time,
			String entity_flg, String auth_flg, String modify_flg, String del_flg, String verify_flg) {
		super();
		this.srl_num = srl_num;
		this.from_form = from_form;
		this.to_form = to_form;
		Status = status;
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
	}
	public FORM_TRANSFER_ENTITY() {
		super();
		// TODO Auto-generated constructor stub
	}
}
