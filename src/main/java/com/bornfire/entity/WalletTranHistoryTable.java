package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BIPS_WALLET_TRAN_HISTORY_TABLE")
public class WalletTranHistoryTable {

	@Id
	private String bips_acct_no;
	private String tran_type;
	private String tran_sub_type;
	private String part_tran_type;
	private String acct_crncy;
	private BigDecimal acct_tran_amt;
	private Date tran_date;
	private Date value_date;
	private String tran_crncy;
	private BigDecimal tran_amt;
	private BigDecimal tran_conv_rate;
	private String merchant_id;
	private String entity_flg;
	private String del_flg;
	private String pstd_flg;
	private Date entry_time;
	private Date pstd_time;
	public String getBips_acct_no() {
		return bips_acct_no;
	}
	public void setBips_acct_no(String bips_acct_no) {
		this.bips_acct_no = bips_acct_no;
	}
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	public String getTran_sub_type() {
		return tran_sub_type;
	}
	public void setTran_sub_type(String tran_sub_type) {
		this.tran_sub_type = tran_sub_type;
	}
	public String getPart_tran_type() {
		return part_tran_type;
	}
	public void setPart_tran_type(String part_tran_type) {
		this.part_tran_type = part_tran_type;
	}
	public String getAcct_crncy() {
		return acct_crncy;
	}
	public void setAcct_crncy(String acct_crncy) {
		this.acct_crncy = acct_crncy;
	}
	public BigDecimal getAcct_tran_amt() {
		return acct_tran_amt;
	}
	public void setAcct_tran_amt(BigDecimal acct_tran_amt) {
		this.acct_tran_amt = acct_tran_amt;
	}
	public Date getTran_date() {
		return tran_date;
	}
	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}
	public Date getValue_date() {
		return value_date;
	}
	public void setValue_date(Date value_date) {
		this.value_date = value_date;
	}
	public String getTran_crncy() {
		return tran_crncy;
	}
	public void setTran_crncy(String tran_crncy) {
		this.tran_crncy = tran_crncy;
	}
	public BigDecimal getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(BigDecimal tran_amt) {
		this.tran_amt = tran_amt;
	}
	public BigDecimal getTran_conv_rate() {
		return tran_conv_rate;
	}
	public void setTran_conv_rate(BigDecimal tran_conv_rate) {
		this.tran_conv_rate = tran_conv_rate;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getEntity_flg() {
		return entity_flg;
	}
	public void setEntity_flg(String entity_flg) {
		this.entity_flg = entity_flg;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getPstd_flg() {
		return pstd_flg;
	}
	public void setPstd_flg(String pstd_flg) {
		this.pstd_flg = pstd_flg;
	}
	public Date getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
	public Date getPstd_time() {
		return pstd_time;
	}
	public void setPstd_time(Date pstd_time) {
		this.pstd_time = pstd_time;
	}
	
	
}
