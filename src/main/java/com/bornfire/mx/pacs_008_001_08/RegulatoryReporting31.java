//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.27 at 05:26:07 PM IST 
//


package com.bornfire.mx.pacs_008_001_08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information needed due to regulatory and/or statutory requirements.
 * 
 * <p>Java class for RegulatoryReporting3__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegulatoryReporting3__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Dtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}StructuredRegulatoryReporting3__1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegulatoryReporting3__1", propOrder = {
    "dtls"
})
public class RegulatoryReporting31 {

    @XmlElement(name = "Dtls", required = true)
    protected StructuredRegulatoryReporting31 dtls;

    /**
     * Gets the value of the dtls property.
     * 
     * @return
     *     possible object is
     *     {@link StructuredRegulatoryReporting31 }
     *     
     */
    public StructuredRegulatoryReporting31 getDtls() {
        return dtls;
    }

    /**
     * Sets the value of the dtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructuredRegulatoryReporting31 }
     *     
     */
    public void setDtls(StructuredRegulatoryReporting31 value) {
        this.dtls = value;
    }

}
