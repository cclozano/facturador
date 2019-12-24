package com.aurora.impuestos;

import com.aurora.impuestos.entidades.FacturaCompra;
import com.aurora.impuestos.entidades.OtroImpuestosCompra;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FacturaCompraTest {
    @Test
    public void getSubTotalOtrosImpuestos1() throws Exception {

        OtroImpuestosCompra otroImpuestosCompra = new OtroImpuestosCompra();
        otroImpuestosCompra.setValor(new BigDecimal("100"));
        otroImpuestosCompra.setPorcentaImpuesto(new BigDecimal("30"));

        OtroImpuestosCompra otroImpuestosCompra1 = new OtroImpuestosCompra();
        otroImpuestosCompra1.setValor(new BigDecimal("100"));
        otroImpuestosCompra1.setPorcentaImpuesto(new BigDecimal("20"));

        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.getOtrosImpestos().add(otroImpuestosCompra);
        facturaCompra.getOtrosImpestos().add(otroImpuestosCompra1);
        assertEquals(new BigDecimal("250.00"),facturaCompra.getTotalOtrosImpuestos());
    }

    @Test
    public void getSubTotalOtrosImpuestos() throws Exception {
        OtroImpuestosCompra otroImpuestosCompra = new OtroImpuestosCompra();
        otroImpuestosCompra.setValor(new BigDecimal("100"));
        otroImpuestosCompra.setPorcentaImpuesto(new BigDecimal("30"));

        OtroImpuestosCompra otroImpuestosCompra1 = new OtroImpuestosCompra();
        otroImpuestosCompra1.setValor(new BigDecimal("100"));
        otroImpuestosCompra1.setPorcentaImpuesto(new BigDecimal("20"));

        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.getOtrosImpestos().add(otroImpuestosCompra);
        facturaCompra.getOtrosImpestos().add(otroImpuestosCompra1);
        assertEquals(new BigDecimal("200.00"),facturaCompra.getSubTotalOtrosImpuestos());


    }

    @Test
    public void getIva() throws Exception {
        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.setSubtotal12(new BigDecimal("16.52"));
        assertEquals(new BigDecimal("1.98"),facturaCompra.getIva());
    }

    @Test
    public void getTotalFactura() throws Exception {


        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.setSubtotal12(new BigDecimal("16.52"));

        assertEquals(new BigDecimal("18.50"),facturaCompra.getTotalFactura());


        FacturaCompra facturaCompra1 = new FacturaCompra();
        facturaCompra1.setSubtotal0(new BigDecimal("17"));
        assertEquals(new BigDecimal("17.00"),facturaCompra1.getTotalFactura());


        FacturaCompra facturaCompra3 = new FacturaCompra();
        facturaCompra3.setSubtotal12(new BigDecimal("16.52"));
        OtroImpuestosCompra otroImpuestosCompra = new OtroImpuestosCompra();
        otroImpuestosCompra.setValor(new BigDecimal("100"));
        otroImpuestosCompra.setPorcentaImpuesto(new BigDecimal("30"));
        facturaCompra3.getOtrosImpestos().add(otroImpuestosCompra);


        assertEquals(new BigDecimal("148.50"),facturaCompra3.getTotalFactura());

    }

}