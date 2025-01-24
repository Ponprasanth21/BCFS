package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkInfo", propOrder = {
    "Priority",
    "IsPossibleDuplicate"
})
public class NetworkInfo {

	 @XmlElement(name = "Priority")
		private String Priority;
	 
	 @XmlElement(name = "IsPossibleDuplicate")
		private boolean IsPossibleDuplicate;

	public String getPriority() {
		return Priority;
	}

	public void setPriority(String priority) {
		Priority = priority;
	}

	public boolean isIsPossibleDuplicate() {
		return IsPossibleDuplicate;
	}

	public void setIsPossibleDuplicate(boolean isPossibleDuplicate) {
		IsPossibleDuplicate = isPossibleDuplicate;
	}

	@Override
	public String toString() {
		return "NetworkInfo [Priority=" + Priority + ", IsPossibleDuplicate=" + IsPossibleDuplicate + "]";
	}
	 
	 
	 
}
