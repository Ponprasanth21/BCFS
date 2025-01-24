package com.bornfire.mcib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "mcib")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mcib", propOrder = {
    "STATUS",
    "CREDIT_PROFILE_RECORDS"
})
public class Mcib {
	
	 @XmlElement(name = "STATUS")
	 private String STATUS;

	 @XmlElement(name = "CREDIT_PROFILE_RECORDS")
	    private CREDIT_PROFILE_RECORDS CREDIT_PROFILE_RECORDS;

	    public String getSTATUS ()
	    {
	        return STATUS;
	    }

	    public void setSTATUS (String STATUS)
	    {
	        this.STATUS = STATUS;
	    }

	    public CREDIT_PROFILE_RECORDS getCREDIT_PROFILE_RECORDS ()
	    {
	        return CREDIT_PROFILE_RECORDS;
	    }

	    public void setCREDIT_PROFILE_RECORDS (CREDIT_PROFILE_RECORDS CREDIT_PROFILE_RECORDS)
	    {
	        this.CREDIT_PROFILE_RECORDS = CREDIT_PROFILE_RECORDS;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [STATUS = "+STATUS+", CREDIT_PROFILE_RECORDS = "+CREDIT_PROFILE_RECORDS+"]";
	    }
}
