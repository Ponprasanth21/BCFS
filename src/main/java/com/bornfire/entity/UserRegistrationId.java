package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Embeddable;

@Embeddable
public class UserRegistrationId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String USER_ID;
	private BigDecimal MOBILE_NUM;
	private BigDecimal customer_id;
	
	
	
	public UserRegistrationId() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserRegistrationId(String uSER_ID, BigDecimal mOBILE_NUM, BigDecimal customer_id) {
		USER_ID = uSER_ID;
		MOBILE_NUM = mOBILE_NUM;
		this.customer_id = customer_id;
	}

	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MOBILE_NUM == null) ? 0 : MOBILE_NUM.hashCode());
		result = prime * result + ((USER_ID == null) ? 0 : USER_ID.hashCode());
		result = prime * result + ((customer_id == null) ? 0 : customer_id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegistrationId other = (UserRegistrationId) obj;
		if (MOBILE_NUM == null) {
			if (other.MOBILE_NUM != null)
				return false;
		} else if (!MOBILE_NUM.equals(other.MOBILE_NUM))
			return false;
		if (USER_ID == null) {
			if (other.USER_ID != null)
				return false;
		} else if (!USER_ID.equals(other.USER_ID))
			return false;
		if (customer_id == null) {
			if (other.customer_id != null)
				return false;
		} else if (!customer_id.equals(other.customer_id))
			return false;
		return true;
	}



	public String getUSER_ID() {
		return USER_ID;
	}



	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}



	public BigDecimal getMOBILE_NUM() {
		return MOBILE_NUM;
	}



	public void setMOBILE_NUM(BigDecimal mOBILE_NUM) {
		MOBILE_NUM = mOBILE_NUM;
	}



	public BigDecimal getCustomer_id() {
		return customer_id;
	}



	public void setCustomer_id(BigDecimal customer_id) {
		this.customer_id = customer_id;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getUser_id() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
