package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class Parameter implements Serializable {

	private String password_life;
	private String password_warning;
	private String alpha_flg;
	private String numeric_flg;
	private String capital_letter_flg;
	private String special_character_flg;
	private String inactive_time;
	private String login_low;
	private String login_high;
	private BigDecimal no_of_passwords;
	private String prev_pass_checks;
	private String permissions;
	private String per_effective_date;
	private String admin;
	private String channel;
	private String entry_user;
	private Date entry_time;
	private String auth_user;
	private Date auth_time;
	private String modify_user;
	private Date modify_time;
	private String entity_flg;
	private String auth_flg;
	private String modify_flg;
	private String session_id;
	private String login_flg;
	private String user_locked_flg;
	private BigDecimal no_of_attmp;
	private String disable_flg;
	private Blob photo;
	private String domain_id;

	public String getPassword_life() {
		return password_life;
	}

	public void setPassword_life(String password_life) {
		this.password_life = password_life;
	}

	public String getPassword_warning() {
		return password_warning;
	}

	public void setPassword_warning(String password_warning) {
		this.password_warning = password_warning;
	}

	public String getAlpha_flg() {
		return alpha_flg;
	}

	public void setAlpha_flg(String alpha_flg) {
		this.alpha_flg = alpha_flg;
	}

	public String getNumeric_flg() {
		return numeric_flg;
	}

	public void setNumeric_flg(String numeric_flg) {
		this.numeric_flg = numeric_flg;
	}

	public String getCapital_letter_flg() {
		return capital_letter_flg;
	}

	public void setCapital_letter_flg(String capital_letter_flg) {
		this.capital_letter_flg = capital_letter_flg;
	}

	public String getSpecial_character_flg() {
		return special_character_flg;
	}

	public void setSpecial_character_flg(String special_character_flg) {
		this.special_character_flg = special_character_flg;
	}

	public String getInactive_time() {
		return inactive_time;
	}

	public void setInactive_time(String inactive_time) {
		this.inactive_time = inactive_time;
	}

	public String getLogin_low() {
		return login_low;
	}

	public void setLogin_low(String login_low) {
		this.login_low = login_low;
	}

	public String getLogin_high() {
		return login_high;
	}

	public void setLogin_high(String login_high) {
		this.login_high = login_high;
	}

	public BigDecimal getNo_of_passwords() {
		return no_of_passwords;
	}

	public void setNo_of_passwords(BigDecimal no_of_passwords) {
		this.no_of_passwords = no_of_passwords;
	}

	public String getPrev_pass_checks() {
		return prev_pass_checks;
	}

	public void setPrev_pass_checks(String prev_pass_checks) {
		this.prev_pass_checks = prev_pass_checks;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getPer_effective_date() {
		return per_effective_date;
	}

	public void setPer_effective_date(String per_effective_date) {
		this.per_effective_date = per_effective_date;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getLogin_flg() {
		return login_flg;
	}

	public void setLogin_flg(String login_flg) {
		this.login_flg = login_flg;
	}

	public String getUser_locked_flg() {
		return user_locked_flg;
	}

	public void setUser_locked_flg(String user_locked_flg) {
		this.user_locked_flg = user_locked_flg;
	}

	public BigDecimal getNo_of_attmp() {
		return no_of_attmp;
	}

	public void setNo_of_attmp(BigDecimal no_of_attmp) {
		this.no_of_attmp = no_of_attmp;
	}

	public String getDisable_flg() {
		return disable_flg;
	}

	public void setDisable_flg(String disable_flg) {
		this.disable_flg = disable_flg;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	public Parameter(String password_life, String password_warning, String alpha_flg, String numeric_flg,
			String capital_letter_flg, String special_character_flg, String inactive_time, String login_low,
			String login_high, BigDecimal no_of_passwords, String prev_pass_checks, String permissions,
			String per_effective_date, String admin, String channel, String entry_user, Date entry_time,
			String auth_user, Date auth_time, String modify_user, Date modify_time, String entity_flg, String auth_flg,
			String modify_flg, String session_id, String login_flg, String user_locked_flg, BigDecimal no_of_attmp,
			String disable_flg, Blob photo, String domain_id) {
		super();
		this.password_life = password_life;
		this.password_warning = password_warning;
		this.alpha_flg = alpha_flg;
		this.numeric_flg = numeric_flg;
		this.capital_letter_flg = capital_letter_flg;
		this.special_character_flg = special_character_flg;
		this.inactive_time = inactive_time;
		this.login_low = login_low;
		this.login_high = login_high;
		this.no_of_passwords = no_of_passwords;
		this.prev_pass_checks = prev_pass_checks;
		this.permissions = permissions;
		this.per_effective_date = per_effective_date;
		this.admin = admin;
		this.channel = channel;
		this.entry_user = entry_user;
		this.entry_time = entry_time;
		this.auth_user = auth_user;
		this.auth_time = auth_time;
		this.modify_user = modify_user;
		this.modify_time = modify_time;
		this.entity_flg = entity_flg;
		this.auth_flg = auth_flg;
		this.modify_flg = modify_flg;
		this.session_id = session_id;
		this.login_flg = login_flg;
		this.user_locked_flg = user_locked_flg;
		this.no_of_attmp = no_of_attmp;
		this.disable_flg = disable_flg;
		this.photo = photo;
		this.domain_id = domain_id;
	}

	@Override
	public String toString() {
		return "Parameter [password_life=" + password_life + ", password_warning=" + password_warning + ", alpha_flg="
				+ alpha_flg + ", numeric_flg=" + numeric_flg + ", capital_letter_flg=" + capital_letter_flg
				+ ", special_character_flg=" + special_character_flg + ", inactive_time=" + inactive_time
				+ ", login_low=" + login_low + ", login_high=" + login_high + ", no_of_passwords=" + no_of_passwords
				+ ", prev_pass_checks=" + prev_pass_checks + ", permissions=" + permissions + ", per_effective_date="
				+ per_effective_date + ", admin=" + admin + ", channel=" + channel + ", entry_user=" + entry_user
				+ ", entry_time=" + entry_time + ", auth_user=" + auth_user + ", auth_time=" + auth_time
				+ ", modify_user=" + modify_user + ", modify_time=" + modify_time + ", entity_flg=" + entity_flg
				+ ", auth_flg=" + auth_flg + ", modify_flg=" + modify_flg + ", session_id=" + session_id
				+ ", login_flg=" + login_flg + ", user_locked_flg=" + user_locked_flg + ", no_of_attmp=" + no_of_attmp
				+ ", disable_flg=" + disable_flg + ", photo=" + photo + ", domain_id=" + domain_id + "]";
	}

	public Parameter() {

	}

}
