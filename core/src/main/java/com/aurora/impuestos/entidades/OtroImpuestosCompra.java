package com.aurora.impuestos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@Entity
@Table(name = "otro_impuesto_compra")
public class OtroImpuestosCompra extends BaseEntity{

    @JoinColumn(name = "factura_compra_id",nullable = false)
    @ManyToOne
    private FacturaCompra facturaCompra;

    @Column(name = "codigo_impuesto")
    private String codigoImpuesto;
    @Column(name = "codigo_porcentaje")
    private String codigoPorcentaje;


    private BigDecimal valor = BigDecimal.ZERO;
    @Column(name = "porcentaje_impuesto")
    private BigDecimal porcentaImpuesto = BigDecimal.ZERO;
    private String descricion;


    public BigDecimal getValorImpuesto()
    {
        return valor.multiply(impuesto());
    }

    public BigDecimal getValorTotal()
    {
        return valor.add(getPorcentaImpuesto());
    }


    private BigDecimal impuesto()
    {
        if(porcentaImpuesto == BigDecimal.ZERO)
            return BigDecimal.ZERO;

        return porcentaImpuesto.divide(new BigDecimal("100"));

    }
}
