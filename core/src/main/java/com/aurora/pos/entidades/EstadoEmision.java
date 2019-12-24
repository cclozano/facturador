package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "ESTADO_EMISION")
public class EstadoEmision extends BaseEntity{

    @Column(name = "estado_recepcion")
    private String estadoRecepcion;
    @Column(name = "estado_autorizacion")
    private String estadoAutorizacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_autorizacion")
    private Date fechaAutorizacion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String xml = "";

    @JoinColumn(name = "documento_electronico_id",nullable = false)
    @NotNull
    @OneToOne
    private DocumentoElectronicoImp documentoElectronico;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "estadoEmision")
    @PrivateOwned
    private List<MensajeProcesoEmision> mensajesRecepcion = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "estadoEmision")
    @PrivateOwned
    private List<MensajeProcesoEmision> mensajesAutorizacion = new ArrayList<>();

    public String getEstado()
    {
        if(estadoAutorizacion!=null && !estadoAutorizacion.isEmpty())
           return estadoAutorizacion;


        if(estadoRecepcion !=null && !estadoRecepcion.isEmpty())
            return estadoRecepcion;

        return "NO PROCESADA";
    }


}
