package com.aurora.pos.entidades;

import com.aurora.inventario.entidades.Producto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DetalleFacturaTest {




    @Test
    public void getTotalImpuestoICE() throws Exception {

        ImpuestoFactura impuesto = new ImpuestoFactura();
        impuesto.setPorcentaje(new BigDecimal("12"));
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setNombre("IVA 12%");

        ImpuestoFactura impuesto1 = new ImpuestoFactura();
        impuesto1.setPorcentaje(new BigDecimal("20"));
        impuesto1.setCodigo("3");
        impuesto1.setCodigoPorcentaje("3610");
        impuesto1.setNombre("ICE 20%");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);
        detalleFactura.addImpuesto(impuesto1);


        TotalImpuesto totalImpuesto =  detalleFactura.getTotalICE();

        assertEquals(new BigDecimal("100"),totalImpuesto.getBaseImponible());
        assertEquals(new BigDecimal("20.0"),totalImpuesto.getValor());
        assertNull(detalleFactura.getTotalImpuestoIva0());

    }






    @Test
    public void getTotalImpuestoIva12() throws Exception {

        ImpuestoFactura impuesto = new ImpuestoFactura();
        impuesto.setPorcentaje(new BigDecimal("12"));
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setNombre("IVA 12%");

        ImpuestoFactura impuesto1 = new ImpuestoFactura();
        impuesto1.setPorcentaje(new BigDecimal("20"));
        impuesto1.setCodigo("3");
        impuesto1.setCodigoPorcentaje("3610");
        impuesto1.setNombre("ICE 20%");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);
        detalleFactura.addImpuesto(impuesto1);


       TotalImpuesto totalImpuesto =  detalleFactura.getTotalImpuestoIva12();

       assertEquals(new BigDecimal("100"),totalImpuesto.getBaseImponible());
       assertEquals(new BigDecimal("12.00"),totalImpuesto.getValor());
       assertNull(detalleFactura.getTotalImpuestoIva0());

    }

    @Test
    public void getTotalImpuestoIva0() throws Exception {

        ImpuestoFactura impuesto = new ImpuestoFactura();
        impuesto.setPorcentaje(new BigDecimal("0"));
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("0");
        impuesto.setNombre("IVA 12%");

        ImpuestoFactura impuesto1 = new ImpuestoFactura();
        impuesto1.setPorcentaje(new BigDecimal("20"));
        impuesto1.setCodigo("3");
        impuesto1.setCodigoPorcentaje("3610");
        impuesto1.setNombre("ICE 20%");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);
        detalleFactura.addImpuesto(impuesto1);


        TotalImpuesto totalImpuesto =  detalleFactura.getTotalImpuestoIva0();

        assertEquals(new BigDecimal("100"),totalImpuesto.getBaseImponible());
        assertEquals(new BigDecimal("0"),totalImpuesto.getValor());

        assertNull(detalleFactura.getTotalImpuestoIva12());

    }







    @Test
    public void getTotalDetalle() throws Exception {
        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setTarifa(new BigDecimal("12"));
        impuesto.setNombre("IVA");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);
        assertEquals(new BigDecimal("112.00"),detalleFactura.getTotalDetalle());

    }


    @Test
    public void getTotalImpuesto() throws Exception {
        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setTarifa(new BigDecimal("12"));
        impuesto.setNombre("IVA");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("3");
        impuesto2.setTarifa(new BigDecimal("14"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("3");
        impuesto3.setCodigoPorcentaje("3");
        impuesto3.setTarifa(new BigDecimal("35"));
        impuesto3.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto3);
        assertEquals(new BigDecimal("61.00"),detalleFactura.getTotalImpuesto());

    }

    @Test
    public void getTotalIva() throws Exception {

        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setTarifa(new BigDecimal("12"));
        impuesto.setNombre("IVA");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("3");
        impuesto2.setTarifa(new BigDecimal("14"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("3");
        impuesto3.setCodigoPorcentaje("3");
        impuesto3.setTarifa(new BigDecimal("35"));
        impuesto3.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto3);


        assertEquals(new BigDecimal("26.00"),detalleFactura.getTotalImpuesto(Impuesto.CODIGO_IVA));
        assertEquals(new BigDecimal("35.00"),detalleFactura.getTotalImpuesto(Impuesto.CODIGO_ICE));
    }

    @Test
    public void setDetalleListaPrecios() throws Exception {

        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("3");
        impuesto.setTarifa(new BigDecimal("12"));
        impuesto.setNombre("IVA");
        ArrayList<Impuesto> impuestos = new ArrayList<>();
        impuestos.add(impuesto);


        Producto producto = new Producto();
        producto.setImpuestos(impuestos);


        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setPrecio(new BigDecimal("10"));
        detalleListaPrecios.setItem(producto);

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setDetalleListaPrecios(detalleListaPrecios);
        detalleFactura.setCantidad(new BigDecimal("1"));

        assertEquals(new BigDecimal("10"),detalleFactura.getPrecioUnitario());
        assertEquals(producto,detalleFactura.getItem());
        assertEquals(new BigDecimal("1.20"),detalleFactura.getTotalImpuesto());

        detalleFactura.setDetalleListaPrecios(detalleListaPrecios);
        assertEquals(new BigDecimal("1.20"),detalleFactura.getTotalImpuesto());

    }

    @Test
    public void getSubTotal() throws Exception {
    }

    @Test
    public void addImpuesto() throws Exception {
        ImpuestoFactura impuesto = new ImpuestoFactura();
        impuesto.setPorcentaje(new BigDecimal("12"));
        impuesto.setCodigo("2");
        impuesto.setCodigo("2");
        impuesto.setNombre("IVA 12%");

        ImpuestoFactura impuesto1 = new ImpuestoFactura();
        impuesto1.setPorcentaje(new BigDecimal("20"));
        impuesto1.setCodigo("3");
        impuesto1.setCodigo("3610");
        impuesto1.setNombre("ICE 20%");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.addImpuesto(impuesto);
        detalleFactura.addImpuesto(impuesto1);

        assertEquals(detalleFactura,impuesto.getDetalleFactura());
        assertEquals(2,detalleFactura.getImpuestos().size());

        assertEquals(new BigDecimal("12.00"),impuesto.getValor());
        assertEquals(new BigDecimal("100"), impuesto.getBaseImponible());


        assertEquals(new BigDecimal("20.0"),impuesto1.getValor());
        assertEquals(new BigDecimal("100"), impuesto1.getBaseImponible());


    }

    @Test
    public void addImpuesto1() throws Exception {
        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2");
        impuesto.setCodigoPorcentaje("2");
        impuesto.setTarifa(new BigDecimal("12"));
        impuesto.setNombre("IVA");

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.addImpuesto(impuesto);

        assertEquals("2",detalleFactura.getImpuestos().get(0).getCodigo());
        assertEquals("2",detalleFactura.getImpuestos().get(0).getCodigoPorcentaje());
        assertEquals(new BigDecimal("12"),detalleFactura.getImpuestos().get(0).getPorcentaje());
        assertEquals("IVA",detalleFactura.getImpuestos().get(0).getNombre());
    }

}