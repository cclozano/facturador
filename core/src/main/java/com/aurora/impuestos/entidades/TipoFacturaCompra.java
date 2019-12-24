package com.aurora.impuestos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@Entity
@Table(name = "TIPO_FACTURA_COMPRAS")
public class TipoFacturaCompra extends BaseEntity {

    private String nombre;
    private String detalle;
    private TipoGasto tipoGasto;
}
