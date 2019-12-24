package com.aurora.impuestos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter
@Entity
@Table(name = "codigo_impuesto_retencion")
public class CodigoImpuestoRetencion extends BaseEntity {
    @NotNull
    private String codigoImpuesto;
    private String codigoRetencion;
    private String descripcion;
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentaje = BigDecimal.ZERO;

    public CodigoImpuestoRetencion() {
    }

    public CodigoImpuestoRetencion(String codigoImpuesto,String codigoRetencion, String descripcion,BigDecimal porcentaje) {
        this.codigoImpuesto = codigoImpuesto;
        this.codigoRetencion = codigoRetencion;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return "["+codigoImpuesto+"][" +codigoRetencion +"] " + descripcion;
    }
}
