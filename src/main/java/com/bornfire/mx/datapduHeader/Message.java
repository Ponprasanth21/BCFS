package com.bornfire.mx.datapduHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {
    "SenderReference",
    "MessageIdentifier",
    "Format",
    "SubFormat",
    "Sender",
    "Receiver",
    "InterfaceInfo",
    "NetworkInfo"
})
public class Message {

	 @XmlElement(name = "SenderReference")
	private String SenderReference;
	 @XmlElement(name = "MessageIdentifier")
	private String MessageIdentifier;
	 @XmlElement(name = "Format")
	private String Format;
	 @XmlElement(name = "SubFormat")
	private String SubFormat;
	 @XmlElement(name = "Sender")
	private Sender Sender;
	 @XmlElement(name = "Receiver")
	private Receiver Receiver;
	 @XmlElement(name = "InterfaceInfo")
	private InterfaceInfo InterfaceInfo;
	 
	 @XmlElement(name = "NetworkInfo")
		private NetworkInfo NetworkInfo;
	
	 
	public String getSenderReference() {
		return SenderReference;
	}
	public void setSenderReference(String senderReference) {
		SenderReference = senderReference;
	}
	public String getMessageIdentifier() {
		return MessageIdentifier;
	}
	public void setMessageIdentifier(String messageIdentifier) {
		MessageIdentifier = messageIdentifier;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getSubFormat() {
		return SubFormat;
	}
	public void setSubFormat(String subFormat) {
		SubFormat = subFormat;
	}
	public Sender getSender() {
		return Sender;
	}
	public void setSender(Sender sender) {
		Sender = sender;
	}
	public Receiver getReceiver() {
		return Receiver;
	}
	public void setReceiver(Receiver receiver) {
		Receiver = receiver;
	}
	public InterfaceInfo getInterfaceInfo() {
		return InterfaceInfo;
	}
	public void setInterfaceInfo(InterfaceInfo interfaceInfo) {
		InterfaceInfo = interfaceInfo;
	}
	
	
	public NetworkInfo getNetworkInfo() {
		return NetworkInfo;
	}
	public void setNetworkInfo(NetworkInfo networkInfo) {
		NetworkInfo = networkInfo;
	}
	@Override
	public String toString() {
		return "Message [SenderReference=" + SenderReference + ", MessageIdentifier=" + MessageIdentifier + ", Format="
				+ Format + ", SubFormat=" + SubFormat + ", Sender=" + Sender + ", Receiver=" + Receiver
				+ ", InterfaceInfo=" + InterfaceInfo + "]";
	}
	
	 
	 
	 
}
