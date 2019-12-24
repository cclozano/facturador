package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.inventario.entidades.Item;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class ListaPrecios extends BaseEntity {


    @Column(name = "fecha_elaboracion")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaElaboracion;
    @NotNull
    @Size(min = 4, max = 25)
    @Column(name = "nombre_corto")
    private String nombreCorto;
    @Size(max = 200)
    private String descripcion;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lista")
    @PrivateOwned
    private Collection<DetalleListaPrecios> detalles = new ArrayList<>();


    public void setDetalles(Collection<DetalleListaPrecios> detalles) {
        detalles.forEach(item -> item.setLista(this));
        this.detalles = detalles;
    }

    public List<Item> getItems()
    {
      return detalles.stream().map(x->x.getItem()).collect(Collectors.toList());
    }



    public DetalleListaPrecios agregarDetalle(DetalleListaPrecios detalleListaPrecios)
    {
        detalleListaPrecios.setLista(this);
        this.detalles.add(detalleListaPrecios);
        return detalleListaPrecios;
    }



}
