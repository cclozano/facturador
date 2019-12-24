//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.05.17 a las 08:22:17 PM COT 
//


package com.aurora.pos.sri.esquemas.factura_v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para compensacionesReembolso complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="compensacionesReembolso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="compensacionReembolso" type="{}compensacion" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compensacionesReembolso", namespace = "", propOrder = {
    "compensacionReembolso"
})
public class CompensacionesReembolso {

    @XmlElement(required = true)
    protected List<Compensacion> compensacionReembolso;

    /**
     * Gets the value of the compensacionReembolso property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compensacionReembolso property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompensacionReembolso().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Compensacion }
     * 
     * 
     */
    public List<Compensacion> getCompensacionReembolso() {
        if (compensacionReembolso == null) {
            compensacionReembolso = new ArrayList<Compensacion>();
        }
        return this.compensacionReembolso;
    }

}
