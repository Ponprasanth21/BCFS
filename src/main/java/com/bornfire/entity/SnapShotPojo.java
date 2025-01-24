package com.bornfire.entity;

import java.math.BigDecimal;

public class SnapShotPojo {
	private String bank_code;
	private String bank_name;
	private String out_no_of_txs;
	private String out_tot_amt;
	private String in_no_of_txs;
	private String in_tot_amt;

	public String getBank_code() {
		return bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public String getOut_no_of_txs() {
		return out_no_of_txs;
	}

	

	public String getIn_no_of_txs() {
		return in_no_of_txs;
	}

	

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public void setOut_no_of_txs(String out_no_of_txs) {
		this.out_no_of_txs = out_no_of_txs;
	}

	

	public void setIn_no_of_txs(String in_no_of_txs) {
		this.in_no_of_txs = in_no_of_txs;
	}

	public String getOut_tot_amt() {
		return out_tot_amt;
	}

	public String getIn_tot_amt() {
		return in_tot_amt;
	}

	public void setOut_tot_amt(String out_tot_amt) {
		this.out_tot_amt = out_tot_amt;
	}

	public void setIn_tot_amt(String in_tot_amt) {
		this.in_tot_amt = in_tot_amt;
	}

	

}
