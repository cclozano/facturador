package com.aurora.pos.server.reportes.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class Detalle {


    private String detalleAdicional1;
    private String detalleAdicional2;
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private BigDecimal cantidad = BigDecimal.ZERO;
    private String descripcion;
    private BigDecimal precioUnitario = BigDecimal.ZERO;
    private BigDecimal descuento = BigDecimal.ZERO;
    private BigDecimal precioTotalSinImpuestos = BigDecimal.ZERO;


  /* <field name="detalleAdicional1" class="java.lang.String"/>
    <field name="detalleAdicional2" class="java.lang.String"/>
    <field name="codigoAuxiliar" class="java.lang.String"/>
    <field name="cantidad" class="java.math.BigDecimal"/>
    <field name="descripcion" class="java.lang.String"/>
    <field name="precioUnitario" class="java.math.BigDecimal"/>
    <field name="descuento" class="java.math.BigDecimal"/>
    <field name="precioTotalSinImpuestos" class="java.math.BigDecimal"/> -->*/

}
