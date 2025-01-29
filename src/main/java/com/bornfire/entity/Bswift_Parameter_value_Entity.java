package com.bornfire.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SWIFT_DETAILS")
public class Bswift_Parameter_value_Entity {
	@Id
	private String	swift_status_m_o_c;
	private String	swift_field;
	private String	swift_field_name;
	private String	swift_content;
	private String	swift_comments;
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
	public String getSwift_status_m_o_c() {
		return swift_status_m_o_c;
	}
	public void setSwift_status_m_o_c(String swift_status_m_o_c) {
		this.swift_status_m_o_c = swift_status_m_o_c;
	}
	public String getSwift_field() {
		return swift_field;
	}
	public void setSwift_field(String swift_field) {
		this.swift_field = swift_field;
	}
	public String getSwift_field_name() {
		return swift_field_name;
	}
	public void setSwift_field_name(String swift_field_name) {
		this.swift_field_name = swift_field_name;
	}
	public String getSwift_content() {
		return swift_content;
	}
	public void setSwift_content(String swift_content) {
		this.swift_content = swift_content;
	}
	public String getSwift_comments() {
		return swift_comments;
	}
	public void setSwift_comments(String swift_comments) {
		this.swift_comments = swift_comments;
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
	public Bswift_Parameter_value_Entity(String swift_status_m_o_c, String swift_field, String swift_field_name,
			String swift_content, String swift_comments, String entry_user, Date entry_time, String modify_user,
			Date modify_time, String verify_user, Date verify_time, String entity_flg, String auth_flg,
			String modify_flg, String del_flg, String verify_flg) {
		super();
		this.swift_status_m_o_c = swift_status_m_o_c;
		this.swift_field = swift_field;
		this.swift_field_name = swift_field_name;
		this.swift_content = swift_content;
		this.swift_comments = swift_comments;
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
	public Bswift_Parameter_value_Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
