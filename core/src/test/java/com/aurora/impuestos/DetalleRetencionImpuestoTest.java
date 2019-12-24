package com.aurora.impuestos;

import com.aurora.impuestos.entidades.DetalleRetencionImpuesto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DetalleRetencionImpuestoTest {
    @Test
    public void valorRetenido() throws Exception {
        DetalleRetencionImpuesto retencion = new DetalleRetencionImpuesto();
        retencion.setPorcentajeRetencion(new BigDecimal("70"));
        retencion.setBaseImponble(new BigDecimal("60.90"));
        assertEquals(new BigDecimal("42.630"),retencion.getValorRetenido());

    }

}