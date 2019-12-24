package com.aurora.pos.entidades;


import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Setter @Getter
public class Impuesto extends BaseEntity{

    public static final String CODIGO_IVA = "2";
    public static final String CODIGO_PORCENTAJE_IVA_12 = "2";
    public static final String CODIGO_PORCENTAJE__IVA_0 = "0";
    public static final String CODIGO_ICE = "3";


    @NotNull
    @Pattern(regexp = "[0-9]",message = "Solo ingrese un numero")
    private String codigo;

    @Pattern(regexp = "([0-9])*")
    @Size(min = 1,message = "Ingrese codigo de porcentaje")
    @Column(name = "codigo_porcentaje")
    private String codigoPorcentaje;

    @NotNull
    @Column(nullable = false,scale = 6,precision = 10)
    private BigDecimal tarifa;

    @NotNull
    @Column(nullable = false)
    private String nombre;

    @Size(max = 255)
    private String descripcion;
}
