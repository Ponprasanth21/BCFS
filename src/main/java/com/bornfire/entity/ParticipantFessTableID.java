package com.bornfire.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class ParticipantFessTableID implements Serializable {
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	CREATION_DATE;
 
	private String	PAYEE_AGENT;
	 
	private String	CATEGORY_NAME;

	public Date getCREATION_DATE() {
		return CREATION_DATE;
	}

	public void setCREATION_DATE(Date cREATION_DATE) {
		CREATION_DATE = cREATION_DATE;
	}

	public String getPAYEE_AGENT() {
		return PAYEE_AGENT;
	}

	public void setPAYEE_AGENT(String pAYEE_AGENT) {
		PAYEE_AGENT = pAYEE_AGENT;
	}

	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	public void setCATEGORY_NAME(String cATEGORY_NAME) {
		CATEGORY_NAME = cATEGORY_NAME;
	}

	public ParticipantFessTableID(Date cREATION_DATE, String pAYEE_AGENT, String cATEGORY_NAME) {
		super();
		CREATION_DATE = cREATION_DATE;
		PAYEE_AGENT = pAYEE_AGENT;
		CATEGORY_NAME = cATEGORY_NAME;
	}

	public ParticipantFessTableID() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
