package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sender", propOrder = {
    "DN",
    "FullName"
})
public class Sender {
 
	 @XmlElement(name = "DN")
	private String DN;
	 @XmlElement(name = "FullName")
	private FullName FullName;
	public String getDN() {
		return DN;
	}
	public void setDN(String dN) {
		DN = dN;
	}
	public FullName getFullName() {
		return FullName;
	}
	public void setFullName(FullName fullName) {
		FullName = fullName;
	}
	 
	 
}
