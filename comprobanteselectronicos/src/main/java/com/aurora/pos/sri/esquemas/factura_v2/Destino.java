//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.05.17 a las 11:06:45 PM COT 
//


package com.aurora.pos.sri.esquemas.factura_v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene la informacion del destinatario
 * 
 * <p>Clase Java para destino complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="destino">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="motivoTraslado" type="{}motivoTraslado"/>
 *         &lt;element name="docAduaneroUnico" type="{}docAduaneroUnico" minOccurs="0"/>
 *         &lt;element name="codEstabDestino" type="{}establecimiento" minOccurs="0"/>
 *         &lt;element name="ruta" type="{}ruta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "destino", namespace = "", propOrder = {
    "motivoTraslado",
    "docAduaneroUnico",
    "codEstabDestino",
    "ruta"
})
public class Destino {

    @XmlElement(required = true)
    protected String motivoTraslado;
    protected String docAduaneroUnico;
    protected String codEstabDestino;
    protected String ruta;

    /**
     * Obtiene el valor de la propiedad motivoTraslado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    /**
     * Define el valor de la propiedad motivoTraslado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoTraslado(String value) {
        this.motivoTraslado = value;
    }

    /**
     * Obtiene el valor de la propiedad docAduaneroUnico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocAduaneroUnico() {
        return docAduaneroUnico;
    }

    /**
     * Define el valor de la propiedad docAduaneroUnico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocAduaneroUnico(String value) {
        this.docAduaneroUnico = value;
    }

    /**
     * Obtiene el valor de la propiedad codEstabDestino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEstabDestino() {
        return codEstabDestino;
    }

    /**
     * Define el valor de la propiedad codEstabDestino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEstabDestino(String value) {
        this.codEstabDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad ruta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Define el valor de la propiedad ruta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuta(String value) {
        this.ruta = value;
    }

}
