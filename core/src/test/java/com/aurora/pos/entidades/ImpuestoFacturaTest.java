package com.aurora.pos.entidades;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ImpuestoFacturaTest {
    @Test
    public void getValor() throws Exception {

        ImpuestoFactura impuestoFactura = new ImpuestoFactura();
        impuestoFactura.setPorcentaje(new BigDecimal("12"));

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuestoFactura);
        assertEquals(new BigDecimal("12.00"),impuestoFactura.getValor());


        ImpuestoFactura impuestoFactura2 = new ImpuestoFactura();
        impuestoFactura2.setPorcentaje(new BigDecimal("9"));

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setCantidad(new BigDecimal("10"));
        detalleFactura2.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura2.addImpuesto(impuestoFactura2);
        assertEquals(new BigDecimal("9.00"),impuestoFactura2.getValor());


    }

    @Test
    public void getBaseImponible() throws Exception {

        ImpuestoFactura impuestoFactura = new ImpuestoFactura();

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuestoFactura);

        assertEquals(new BigDecimal("100"),impuestoFactura.getBaseImponible());

        ImpuestoFactura impuestoFactura2 = new ImpuestoFactura();

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setCantidad(new BigDecimal("10"));
        detalleFactura2.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura2.setDescuento(new BigDecimal("10"));
        detalleFactura2.addImpuesto(impuestoFactura2);

        assertEquals(new BigDecimal("90"),impuestoFactura2.getBaseImponible());


    }

}