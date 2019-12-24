package com.aurora.pos.server.reportes.dto;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.aurora.pos.entidades.Factura;

public class FacturasVentaDto {

    private String numeroFactura;
    private String fecha;
    private String nombreCliente;
    private String rucCLiente;
    private String autorizacion;
    private BigDecimal subtotal12 = BigDecimal.ZERO;
    private BigDecimal subtotal0 = BigDecimal.ZERO;
    private BigDecimal iva12 = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    private List<RetencionImpuestoDto> retenciones = new ArrayList<>();

    public FacturasVentaDto(Factura factura) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        numeroFactura = factura.getNumeroCompleto();
        fecha = simpleDateFormat.format(factura.getFechaEmision());
        nombreCliente = factura.getCliente().getNombre();
        rucCLiente = factura.getCliente().getIdentificacion();
        autorizacion = factura.getClaveAcceso();
        subtotal12 = factura.getSubTotalIva12().setScale(2,BigDecimal.ROUND_HALF_UP);
        subtotal0 = factura.getSubTotalZero().setScale(2,BigDecimal.ROUND_HALF_UP);
        iva12 = factura.getTotalIva12().setScale(2,BigDecimal.ROUND_HALF_UP);
        total = factura.getTotalFactura().setScale(2,BigDecimal.ROUND_HALF_UP);
        if(factura.getRetencionImpuesto() != null )retenciones.add(new RetencionImpuestoDto(factura.getRetencionImpuesto()));
    }

    public List<RetencionImpuestoDto> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<RetencionImpuestoDto> retenciones) {
        this.retenciones = retenciones;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRucCLiente() {
        return rucCLiente;
    }

    public void setRucCLiente(String rucCLiente) {
        this.rucCLiente = rucCLiente;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public BigDecimal getSubtotal12() {
        return subtotal12;
    }

    public void setSubtotal12(BigDecimal subtotal12) {
        this.subtotal12 = subtotal12;
    }

    public BigDecimal getSubtotal0() {
        return subtotal0;
    }

    public void setSubtotal0(BigDecimal subtotal0) {
        this.subtotal0 = subtotal0;
    }

    public BigDecimal getIva12() {
        return iva12;
    }

    public void setIva12(BigDecimal iva12) {
        this.iva12 = iva12;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
