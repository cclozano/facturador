package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by max on 10/08/17.
 */

@Entity
@Setter @Getter
@Table( name="PUNTO_EMISION",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"codigo","establecimiento_id"})
)
public class PuntoEmision extends BaseEntity{

    @NotNull(message = "Codigo es requerido")
    @Size(max = 3,min = 3,message = "Debe tener tres numeros")
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Solo Numeros")
    private String codigo;
    private String descripcion;

    @Column(name = "numero_ultima_factura")
    private long numeroUtimaFactura=0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ESTABLECIMIENTO_ID",nullable = false)
    private Establecimiento establecimiento;


    public Long getSiguienteNumero()
    {
        return numeroUtimaFactura + 1;
    }
}
