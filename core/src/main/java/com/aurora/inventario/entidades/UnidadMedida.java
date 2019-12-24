package com.aurora.inventario.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter @Setter
@Table(name = "unidad_medida", uniqueConstraints = @UniqueConstraint(columnNames={"codigo"}))
public class UnidadMedida extends BaseEntity {

    private String codigo;
    private String nombre;
}
