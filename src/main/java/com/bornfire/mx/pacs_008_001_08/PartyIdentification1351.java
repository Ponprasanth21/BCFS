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
 * Specifies the identification of a person or an organisation.
 * 
 * <p>Java class for PartyIdentification135__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyIdentification135__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="PstlAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PostalAddress24__1" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Party38Choice__1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification135__1", propOrder = {
    "nm",
    "pstlAdr",
    "id"
})
public class PartyIdentification1351 {

    @XmlElement(name = "Nm")
    protected String nm;
    @XmlElement(name = "PstlAdr")
    protected PostalAddress241 pstlAdr;
    @XmlElement(name = "Id")
    protected Party38Choice1 id;

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the pstlAdr property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddress241 }
     *     
     */
    public PostalAddress241 getPstlAdr() {
        return pstlAdr;
    }

    /**
     * Sets the value of the pstlAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddress241 }
     *     
     */
    public void setPstlAdr(PostalAddress241 value) {
        this.pstlAdr = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Party38Choice1 }
     *     
     */
    public Party38Choice1 getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party38Choice1 }
     *     
     */
    public void setId(Party38Choice1 value) {
        this.id = value;
    }

}
