package com.bornfire.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BIPS_MERCHANT_CATEGORY_CODE_TABLE")
public class MerchantCategoryCodeEntity {
	
	@Id
	private String	merchant_code;
	private String	merchant_desc;
	private String	entry_user;
	private Date	entry_time;
	private String	entity_cre_flg;
	private String	del_flg;
	private String	auth_user;
	private Date	auth_time;
	private String	modify_user;
	private Date	modify_time;
	private String	modify_flg;
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getMerchant_desc() {
		return merchant_desc;
	}
	public void setMerchant_desc(String merchant_desc) {
		this.merchant_desc = merchant_desc;
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
	public String getAuth_user() {
		return auth_user;
	}
	public void setAuth_user(String auth_user) {
		this.auth_user = auth_user;
	}
	public Date getAuth_time() {
		return auth_time;
	}
	public void setAuth_time(Date auth_time) {
		this.auth_time = auth_time;
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
	public String getModify_flg() {
		return modify_flg;
	}
	public void setModify_flg(String modify_flg) {
		this.modify_flg = modify_flg;
	}
	public MerchantCategoryCodeEntity(String merchant_code, String merchant_desc, String entry_user, Date entry_time,
			String entity_cre_flg, String del_flg, String auth_user, Date auth_time, String modify_user,
			Date modify_time, String modify_flg) {
		super();
		this.merchant_code = merchant_code;
		this.merchant_desc = merchant_desc;
		this.entry_user = entry_user;
		this.entry_time = entry_time;
		this.entity_cre_flg = entity_cre_flg;
		this.del_flg = del_flg;
		this.auth_user = auth_user;
		this.auth_time = auth_time;
		this.modify_user = modify_user;
		this.modify_time = modify_time;
		this.modify_flg = modify_flg;
	}
	public MerchantCategoryCodeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
