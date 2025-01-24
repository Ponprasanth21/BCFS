package com.bornfire.entity;

import java.io.Serializable;

import javax.persistence.Id;


public class WalletFeesID implements Serializable {

@Id	
   private String	FEE_SRL_NO;

public String getFEE_SRL_NO() {
	return FEE_SRL_NO;
}

public void setFEE_SRL_NO(String fEE_SRL_NO) {
	FEE_SRL_NO = fEE_SRL_NO;
}





}
