package com.bornfire.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RTPbulkTransferRequest {
	
	private RemitterAccount RemitterAccount;
	
	
	private List<BenAccount> BenAccount;
	
	public RemitterAccount getRemitterAccount() {
		return RemitterAccount;
	}
	public void setRemitterAccount(RemitterAccount remitterAccount) {
		RemitterAccount = remitterAccount;
	}
	public List<BenAccount> getBenAccount() {
		return BenAccount;
	}
	public void setBenAccount(List<BenAccount> benAccount) {
		BenAccount = benAccount;
	}
	
	@Override
	public String toString() {
		return "RTPbulkTransferRequest [RemitterAccount=" + RemitterAccount + ", BenAccount=" + BenAccount + "]";
	}
	
	

}
