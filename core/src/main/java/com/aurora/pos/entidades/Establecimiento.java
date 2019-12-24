package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 10/08/17.
 */
@Entity
@Getter @Setter
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"codigo"})
)
public class Establecimiento extends BaseEntity {


    @NotNull(message = "Codigo es requerido")
    @Size(max = 3,min = 3,message = "Debe tener tres numeros")
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Solo Numeros")
    private String codigo;
    @Size(max = 100, min = 3)
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Column(name = "nombre_corto")
    @Size(max = 50, min = 3)
    private String nombreCorto;

    @NotNull @Size(min = 10, max=200)
    private String direccion;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "establecimiento")
    @PrivateOwned
    private List<PuntoEmision> puntos = new ArrayList<>();




}
