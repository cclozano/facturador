package com.aurora.inventario.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.pos.entidades.Impuesto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"codigo"})
)
public class Item extends BaseEntity{

    @NotNull
    @Size(min = 3,max = 15)
    private String codigo;
    @NotNull
    @Size(max = 30)
    private String nombre;
    private String descripcion;

    @ManyToMany
    private List<Impuesto> impuestos = new ArrayList<>();



    public String getNombreCompleto()
    {
        return  "[" + this.codigo + "] " + nombre;
    }
}
