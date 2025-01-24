package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FullName", propOrder = {
    "X1",
    "X2"
})
public class FullName {
	

	 @XmlElement(name = "X1")
	private String X1;
	 @XmlElement(name = "X2")
	private String X2;
	public String getX1() {
		return X1;
	}
	public void setX1(String x1) {
		X1 = x1;
	}
	public String getX2() {
		return X2;
	}
	public void setX2(String x2) {
		X2 = x2;
	}
	 
	 

}
