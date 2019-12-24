//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.05.17 a las 11:10:14 PM COT 
//


package com.aurora.pos.sri.esquemas.notadebito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * DetalleFacturaEntity de una nota de credito.
 * 
 * <p>Clase Java para detalle complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="detalle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="motivoModificacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalle", namespace = "", propOrder = {
    "motivoModificacion"
})
public class Detalle {

    @XmlElement(required = true)
    protected String motivoModificacion;

    /**
     * Obtiene el valor de la propiedad motivoModificacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoModificacion() {
        return motivoModificacion;
    }

    /**
     * Define el valor de la propiedad motivoModificacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoModificacion(String value) {
        this.motivoModificacion = value;
    }

}
