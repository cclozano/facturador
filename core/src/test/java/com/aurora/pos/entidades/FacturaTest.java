package com.aurora.pos.entidades;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class FacturaTest {
    @Test
    public void getSubTotalIva12() throws Exception {
        Factura factura = new Factura();
        factura.getDetalles().add(getFactura().getDetalles().get(0));
        assertEquals(new BigDecimal("100"),factura.getSubTotalIva12());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalZero());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalIce());
    }

    @Test
    public void getSubTotalZero() throws Exception {

        Factura factura = new Factura();
        factura.getDetalles().add(getFactura().getDetalles().get(1));
        assertEquals(new BigDecimal("50"),factura.getSubTotalZero());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalIva12());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalIce());
    }

    @Test
    public void getSubTotalICE() throws Exception {

        Factura factura = new Factura();
        factura.getDetalles().add(getFactura().getDetalles().get(3));
        assertEquals(new BigDecimal("90"),factura.getSubTotalIce());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalIva12());
        assertEquals(BigDecimal.ZERO,factura.getSubTotalZero());
    }


    public Factura getFactura()
    {
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("2");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA 12%");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("2");
        impuesto3.setCodigoPorcentaje("0");
        impuesto3.setTarifa(new BigDecimal("0"));
        impuesto3.setNombre("IVA 0%");
        detalleFactura2.addImpuesto(impuesto3);

        DetalleFactura detalleFactura3 = new DetalleFactura();
        detalleFactura3.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura3.setCantidad(new BigDecimal("10"));
        Impuesto impuesto4 = new Impuesto();
        impuesto4.setCodigo("2");
        impuesto4.setCodigoPorcentaje("2");
        impuesto4.setTarifa(new BigDecimal("12"));
        impuesto4.setNombre("IVA 12%");
        detalleFactura3.addImpuesto(impuesto4);

        DetalleFactura detalleFactura4 = new DetalleFactura();
        detalleFactura4.setPrecioUnitario(new BigDecimal("9"));
        detalleFactura4.setCantidad(new BigDecimal("10"));
        Impuesto impuesto5 = new Impuesto();
        impuesto5.setCodigo("3");
        impuesto5.setCodigoPorcentaje("2");
        impuesto5.setTarifa(new BigDecimal("3610"));
        impuesto5.setNombre("ICE 20%");
        detalleFactura4.addImpuesto(impuesto5);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);
        factura.agregarDetalle(detalleFactura3);
        factura.agregarDetalle(detalleFactura4);
        return factura;
    }


    @Test
    public void generarFormaPago() throws Exception {

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setDescuento(new BigDecimal("2"));


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.generarFormaPago();

        assertEquals(1,factura.getFormaPagos().size());
        FormaPago formaPago = factura.getFormaPagos().get(0);
        assertEquals(formaPago.getValor(),factura.getTotalFactura());
        assertEquals(FormaPago.TipoFormaPago.CONTADO, formaPago.getTipo());


    }

    @Test
    public void setPuntoEmisionNumeroFactura() throws Exception {
        PuntoEmision puntoEmision = new PuntoEmision();
        puntoEmision.setNumeroUtimaFactura(10);
        Factura factura = new Factura();
        factura.setPuntoEmision(puntoEmision);
        assertEquals(new Long(0),factura.getNumero());


    }

    @Test
    public void getNumeroDisplay() throws Exception {
       Factura factura = new Factura();
       factura.setNumero(555l);
       String numero = factura.getNumeroDisplay();
       assertEquals("000000555",numero);
    }

    @Test
    public void getTotalFactura() throws Exception {
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));
        detalleFactura.setDescuento(new BigDecimal("2"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("3");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("3");
        impuesto3.setCodigoPorcentaje("3");
        impuesto3.setTarifa(new BigDecimal("35"));
        impuesto3.setNombre("ICE");
        detalleFactura2.addImpuesto(impuesto3);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);
        assertEquals(new BigDecimal("177.26"),factura.getTotalFactura());

    }

    @Test
    public void getTotalIva() throws Exception {


        Factura factura = getFactura();
        BigDecimal totalIva =   factura.getTotalIva12();


        assertEquals(new BigDecimal("18.00"),totalIva);




        /*DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("2");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("2");
        impuesto3.setCodigoPorcentaje("2");
        impuesto3.setTarifa(new BigDecimal("12"));
        impuesto3.setNombre("IVA");
        detalleFactura2.addImpuesto(impuesto3);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);

        assertEquals(new BigDecimal("18.00"),factura.getTotalIva12());*/
    }

    @Test
    public void getTotalIce() throws Exception {
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("3");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("3");
        impuesto3.setCodigoPorcentaje("3");
        impuesto3.setTarifa(new BigDecimal("35"));
        impuesto3.setNombre("ICE");
        detalleFactura2.addImpuesto(impuesto3);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);

        assertEquals(new BigDecimal("17.50"),factura.getTotalIce());
    }

    @Test
    public void getTotalImpuestos() throws Exception {

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("3");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("3");
        impuesto3.setCodigoPorcentaje("3");
        impuesto3.setTarifa(new BigDecimal("12"));
        impuesto3.setNombre("IVA");
        detalleFactura2.addImpuesto(impuesto3);

        DetalleFactura detalleFactura3 = new DetalleFactura();
        detalleFactura3.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura3.setCantidad(new BigDecimal("10"));
        Impuesto impuesto4 = new Impuesto();
        impuesto4.setCodigo("2");
        impuesto4.setCodigoPorcentaje("2");
        impuesto4.setTarifa(new BigDecimal("12"));
        impuesto4.setNombre("IVA 12%");
        detalleFactura3.addImpuesto(impuesto4);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);
        assertEquals(new BigDecimal("18.00"),factura.getTotalImpuestos());








    }


    @Test
    public void getTotalesImpuestos() {
      /*  DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura.setCantidad(new BigDecimal("10"));

        Impuesto impuesto2 = new Impuesto();
        impuesto2.setCodigo("2");
        impuesto2.setCodigoPorcentaje("2");
        impuesto2.setTarifa(new BigDecimal("12"));
        impuesto2.setNombre("IVA 12%");
        detalleFactura.addImpuesto(impuesto2);

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura2.setCantidad(new BigDecimal("10"));
        Impuesto impuesto3 = new Impuesto();
        impuesto3.setCodigo("2");
        impuesto3.setCodigoPorcentaje("0");
        impuesto3.setTarifa(new BigDecimal("0"));
        impuesto3.setNombre("IVA 0%");
        detalleFactura2.addImpuesto(impuesto3);

        DetalleFactura detalleFactura3 = new DetalleFactura();
        detalleFactura3.setPrecioUnitario(new BigDecimal("5"));
        detalleFactura3.setCantidad(new BigDecimal("10"));
        Impuesto impuesto4 = new Impuesto();
        impuesto4.setCodigo("2");
        impuesto4.setCodigoPorcentaje("2");
        impuesto4.setTarifa(new BigDecimal("12"));
        impuesto4.setNombre("IVA 12%");
        detalleFactura3.addImpuesto(impuesto4);

        DetalleFactura detalleFactura4 = new DetalleFactura();
        detalleFactura4.setPrecioUnitario(new BigDecimal("10"));
        detalleFactura4.setCantidad(new BigDecimal("10"));
        Impuesto impuesto5 = new Impuesto();
        impuesto5.setCodigo("3");
        impuesto5.setCodigoPorcentaje("2");
        impuesto5.setTarifa(new BigDecimal("3610"));
        impuesto5.setNombre("ICE 20%");
        detalleFactura4.addImpuesto(impuesto5);


        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);
        factura.agregarDetalle(detalleFactura3);
        factura.agregarDetalle(detalleFactura4);*/


        Factura factura = getFactura();
        List<TotalImpuesto> totales = factura.getTotalesImpuestos();
        assertEquals(2,totales.size());
        assertEquals(new BigDecimal("150"),totales.get(0).getBaseImponible());
        assertEquals(new BigDecimal("50"),totales.get(1).getBaseImponible());

        assertEquals(new BigDecimal("18.00"),totales.get(0).getValor());
        assertEquals(new BigDecimal("0"),totales.get(1).getValor());


    }



    @Test
    public void agregarDetalle() throws Exception {
    }


    @Test
    public void getTotalDescuento() throws Exception {

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("5"));
        detalleFactura.setDescuento(new BigDecimal("1"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setCantidad(new BigDecimal("2.5"));
        detalleFactura2.setDescuento(new BigDecimal("0.5"));
        detalleFactura2.setPrecioUnitario(new BigDecimal("4"));

        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);



        assertEquals(new BigDecimal("1.5"),factura.getTotalDescuento());
    }


    @Test
    public void getTotalSinImpuestos() throws Exception {

        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setCantidad(new BigDecimal("5"));
        detalleFactura.setPrecioUnitario(new BigDecimal("10"));

        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setCantidad(new BigDecimal("2.5"));
        detalleFactura2.setPrecioUnitario(new BigDecimal("4"));

        Factura factura = new Factura();
        factura.agregarDetalle(detalleFactura);
        factura.agregarDetalle(detalleFactura2);


        assertEquals(new BigDecimal("60.0"),factura.getTotalSinImpuestos());
    }

}