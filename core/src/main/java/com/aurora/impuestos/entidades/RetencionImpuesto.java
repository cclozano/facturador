package com.aurora.impuestos.entidades;


import com.aurora.framework.data.BaseEntity;
import com.aurora.pos.entidades.Factura;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
///@Table(name = "retencion_impuesto")

public class RetencionImpuesto extends BaseEntity {

    @Column(name = "numero_rencion")
    @NotNull
    private String numero;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "xml_retencion")
    public String xml;

    @Column(name = "clave_acceso_retencion")
    @Size(max = 50)
    private String claveAcceso;


    @Temporal(value = TemporalType.DATE)
    @NotNull
    @Column(name = "fecha_retencion")
    private Date fecha;

    @Column(name = "path_foto_retencion")
    private String pathFoto;




    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DetalleRetencionImpuesto> detalles = new ArrayList<>();

}
