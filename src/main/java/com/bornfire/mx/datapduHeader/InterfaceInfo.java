package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceInfo", propOrder = {
    "MessageCreator",
    "MessageContext",
    "MessageNature",
    "Sumid",
    "ServiceURI",
    "MessageTypeURI"
})
public class InterfaceInfo {
	 @XmlElement(name = "MessageCreator")
		private String MessageCreator;
	 @XmlElement(name = "MessageContext")
		private String MessageContext;
	 @XmlElement(name = "MessageNature")
		private String MessageNature;
	 @XmlElement(name = "Sumid")
		private String Sumid;
	 @XmlElement(name = "ServiceURI")
		private String ServiceURI;
	 @XmlElement(name = "MessageTypeURI")
		private String MessageTypeURI;
	public String getMessageCreator() {
		return MessageCreator;
	}
	public void setMessageCreator(String messageCreator) {
		MessageCreator = messageCreator;
	}
	public String getMessageContext() {
		return MessageContext;
	}
	public void setMessageContext(String messageContext) {
		MessageContext = messageContext;
	}
	public String getMessageNature() {
		return MessageNature;
	}
	public void setMessageNature(String messageNature) {
		MessageNature = messageNature;
	}
	public String getSumid() {
		return Sumid;
	}
	public void setSumid(String sumid) {
		Sumid = sumid;
	}
	public String getServiceURI() {
		return ServiceURI;
	}
	public void setServiceURI(String serviceURI) {
		ServiceURI = serviceURI;
	}
	public String getMessageTypeURI() {
		return MessageTypeURI;
	}
	public void setMessageTypeURI(String messageTypeURI) {
		MessageTypeURI = messageTypeURI;
	}
	 
	 

}
