package com.bornfire.mcib;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CREDIT_PROFILE_RECORDS", propOrder = {
    "CREDIT_PROFILE"
})

public class CREDIT_PROFILE_RECORDS {
	
	 @XmlElement(name = "CREDIT_PROFILE")
	private List<CREDIT_PROFILE> CREDIT_PROFILE;

	public List<CREDIT_PROFILE> getCREDIT_PROFILE() {
		return CREDIT_PROFILE;
	}

	public void setCREDIT_PROFILE(List<CREDIT_PROFILE> cREDIT_PROFILE) {
		CREDIT_PROFILE = cREDIT_PROFILE;
	}

	
}
