package com.aurora.impuestos;

import com.aurora.impuestos.entidades.OtroImpuestosCompra;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OtroImpuestosCompraTest {
    @Test
    public void getValorImpuesto() throws Exception {

        OtroImpuestosCompra otroImpuestosCompra = new OtroImpuestosCompra();
        otroImpuestosCompra.setValor(new BigDecimal("100"));
        otroImpuestosCompra.setPorcentaImpuesto(new BigDecimal("30"));

        assertEquals(new BigDecimal("30.0"),otroImpuestosCompra.getValorImpuesto());

    }

    @Test
    public void getValorTotal() throws Exception {
        OtroImpuestosCompra otroImpuestosCompra = new OtroImpuestosCompra();
        otroImpuestosCompra.setValor(new BigDecimal("100"));
        otroImpuestosCompra.setPorcentaImpuesto(new BigDecimal("30"));

        assertEquals(new BigDecimal("130"),otroImpuestosCompra.getValorTotal());
    }

}