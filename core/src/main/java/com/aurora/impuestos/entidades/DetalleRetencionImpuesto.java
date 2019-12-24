package com.aurora.impuestos.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.pos.entidades.Impuesto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@Entity
@Table(name = "detalle_retencion_impuesto")
public class DetalleRetencionImpuesto extends BaseEntity{


    private String codigoImpuesto;
    private String codigoRetencion;
    private String nombreImpuesto;
    private BigDecimal porcentajeRetencion=BigDecimal.ZERO;
    private BigDecimal baseImponble = BigDecimal.ZERO;

   @JoinColumn(name = "retencion_impuesto_id",nullable = false)
    @ManyToOne
    private RetencionImpuesto retencionImpuesto;

    public BigDecimal getValorRetenido()
    {
        return baseImponble.multiply(getImpuesto());
    }

    private BigDecimal getImpuesto()
    {
        if(porcentajeRetencion == BigDecimal.ZERO)
            return BigDecimal.ZERO;
        return porcentajeRetencion.divide(new BigDecimal("100"));
    }

    @Transient
    private CodigoImpuestoRetencion impuestoRetencion;

    public void setImpuestoRetencion(CodigoImpuestoRetencion impuesto)
    {

        codigoImpuesto = impuesto.getCodigoImpuesto();
        codigoRetencion = impuesto.getCodigoRetencion();
        nombreImpuesto = impuesto.getDescripcion();
        porcentajeRetencion = impuesto.getPorcentaje();
        this.impuestoRetencion = impuesto;

    }

}
