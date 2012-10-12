//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.08 at 03:32:31 PM BST 
//


package org.openworm.simulationengine.model.sph;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SPHModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SPHModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="particles" type="{http://www.example.org/SPHSchema}SPHParticle" maxOccurs="unbounded"/>
 *         &lt;element name="cells" type="{http://www.example.org/SPHSchema}SPHCell" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="cellX" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="cellY" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="cellZ" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SPHModel", propOrder = {
    "particles",
    "cells"
})
public class SPHModel {

    @XmlElement(required = true)
    protected List<SPHParticle> particles;
    @XmlElement(required = true)
    protected List<SPHCell> cells;
    @XmlAttribute(name = "cellX")
    protected Integer cellX;
    @XmlAttribute(name = "cellY")
    protected Integer cellY;
    @XmlAttribute(name = "cellZ")
    protected Integer cellZ;

    /**
     * Gets the value of the particles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the particles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SPHParticle }
     * 
     * 
     */
    public List<SPHParticle> getParticles() {
        if (particles == null) {
            particles = new ArrayList<SPHParticle>();
        }
        return this.particles;
    }

    /**
     * Gets the value of the cells property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cells property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCells().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SPHCell }
     * 
     * 
     */
    public List<SPHCell> getCells() {
        if (cells == null) {
            cells = new ArrayList<SPHCell>();
        }
        return this.cells;
    }

    /**
     * Gets the value of the cellX property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCellX() {
        return cellX;
    }

    /**
     * Sets the value of the cellX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCellX(Integer value) {
        this.cellX = value;
    }

    /**
     * Gets the value of the cellY property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCellY() {
        return cellY;
    }

    /**
     * Sets the value of the cellY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCellY(Integer value) {
        this.cellY = value;
    }

    /**
     * Gets the value of the cellZ property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCellZ() {
        return cellZ;
    }

    /**
     * Sets the value of the cellZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCellZ(Integer value) {
        this.cellZ = value;
    }

}