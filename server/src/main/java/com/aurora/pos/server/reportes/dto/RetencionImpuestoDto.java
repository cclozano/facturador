package com.aurora.pos.server.reportes.dto;

import com.aurora.impuestos.entidades.DetalleRetencionImpuesto;
import com.aurora.impuestos.entidades.RetencionImpuesto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class RetencionImpuestoDto {
    private String numero;
    private String claveAcceso;
    private Date fecha;
    private List<DetalleRetencionImpuestoDto> detalles = new ArrayList<>();

    public RetencionImpuestoDto(RetencionImpuesto r) {
        this.numero = r.getNumero();
        this.claveAcceso = r.getClaveAcceso();
        this.fecha = r.getFecha();
        for (DetalleRetencionImpuesto d : r.getDetalles())
        {
            detalles.add(new DetalleRetencionImpuestoDto(d));
        }
    }
}
