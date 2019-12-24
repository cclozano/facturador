package com.aurora.pos.entidades;


import com.aurora.framework.data.BaseEntity;
import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.entidades.UnidadMedida;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Setter @Getter
@Table(name = "detalle_lista_precios")
public class DetalleListaPrecios extends BaseEntity {

    @Column(nullable = false, precision = 10,scale = 6)
    private BigDecimal precio = BigDecimal.ZERO;

    @JoinColumn(name = "item_id",nullable = false)
    @NotNull
    @ManyToOne
    private Item item;

    @ManyToOne
    @JoinColumn(nullable = false,name = "LISTA_ID")
    @NotNull
    private ListaPrecios lista;

    @NotNull
    @JoinColumn(name = "unidad_medida_id")
    @ManyToOne
    private UnidadMedida unidadMedida;


    public String getDescripcion()
    {
        return this.item.getNombreCompleto() + "($" + this.precio + ")";
    }
}
