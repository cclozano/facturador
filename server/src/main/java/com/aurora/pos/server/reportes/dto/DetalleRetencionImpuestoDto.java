package com.aurora.pos.server.reportes.dto;

import com.aurora.impuestos.entidades.DetalleRetencionImpuesto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class DetalleRetencionImpuestoDto {
    private String codigoImpuesto;
    private String codigoRetencion;
    private String nombreImpuesto;
    private BigDecimal porcentajeRetencion=BigDecimal.ZERO;
    private BigDecimal baseImponble = BigDecimal.ZERO;
    private BigDecimal valorRetenido;

    public DetalleRetencionImpuestoDto(DetalleRetencionImpuesto d) {
        this.codigoImpuesto = d.getCodigoImpuesto();
        this.codigoRetencion = d.getCodigoRetencion();
        this.nombreImpuesto = d.getNombreImpuesto();
        this.porcentajeRetencion = d.getPorcentajeRetencion();
        this.baseImponble = d.getBaseImponble();
        this.valorRetenido = d.getValorRetenido();
    }
}
