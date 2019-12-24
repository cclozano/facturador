package com.aurora.pos.entidades;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public  class TotalImpuesto
{
    private String codigo;
    private String codigoPorcentaje;
    private BigDecimal baseImponible= BigDecimal.ZERO;
    private BigDecimal valor = BigDecimal.ZERO;

    public TotalImpuesto(String codigo,String codigoPorcentaje)
    {
        this.codigo = codigo;
        this.codigoPorcentaje = codigoPorcentaje;
    }
}
