package com.bornfire.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class MCCreditTransferResponse {
	private String TranID;
	private String TranDateTime;
	private String Status;
	
	@JsonProperty("Error")
	private String Error;
	@JsonProperty("Error_Desc")
	private List<String> Error_Desc;
	
	
	public MCCreditTransferResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MCCreditTransferResponse(String tranID, String tranDateTime) {
		super();
		TranID = tranID;
		TranDateTime = tranDateTime;
		
	}

	public String getTranID() {
		return TranID;
	}


	public void setTranID(String tranID) {
		TranID = tranID;
	}


	public String getTranDateTime() {
		return TranDateTime;
	}


	public void setTranDateTime(String tranDateTime) {
		TranDateTime = tranDateTime;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}

	@JsonProperty("Error")
	public String getError() {
		return Error;
	}
	@JsonProperty("Error")
	public void setError(String error) {
		Error = error;
	}
	@JsonProperty("Error_Desc")
	public List<String> getError_Desc() {
		return Error_Desc;
	}
	@JsonProperty("Error_Desc")
	public void setError_Desc(List<String> error_Desc) {
		Error_Desc = error_Desc;
	}

}
