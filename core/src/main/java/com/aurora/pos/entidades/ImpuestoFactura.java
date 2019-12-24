package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@Table(name = "impuesto_factura")
public class ImpuestoFactura extends BaseEntity{

    @NotNull
    @Column(nullable = false)
    private String codigo;
    @NotNull
    @Column(name = "codigo_porcentaje",nullable = false)
    private String codigoPorcentaje;
    private BigDecimal porcentaje;
    private String nombre;

    @JoinColumn(name = "detalle_factura_id",nullable = false)
    @ManyToOne
    @NotNull
    private DetalleFactura detalleFactura;

    public BigDecimal getValor()
    {
        return getBaseImponible().multiply( porcentaje.divide(new BigDecimal("100"))     );

    }

    public BigDecimal getBaseImponible()
    {
        return detalleFactura.getSubTotal();
    }

}
