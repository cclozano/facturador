package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter @Getter
@Table(name = "detalle_adicional")
public class DetalleAdicional extends BaseEntity{

    private String nombre;
    private String valor;
}
