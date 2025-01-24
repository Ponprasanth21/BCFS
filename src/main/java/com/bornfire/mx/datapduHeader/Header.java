package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Header")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
    "Message"
})
public class Header {
	    @XmlElement(name = "Message")
	    protected Message Message;

		public Message getMessage() {
			return Message;
		}

		public void setMessage(Message Message) {
			this.Message = Message;
		}

		@Override
		public String toString() {
			return "Header [message=" + Message + "]";
		}
	    
	    
	    
}
