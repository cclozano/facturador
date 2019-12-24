package com.aurora.pos.entidades;


import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "mensaje_proceso_emision")
public class MensajeProcesoEmision extends BaseEntity{
    private String codigo;
    private String mensaje;
    @Column(name = "informacion_adicional")
    private String informacionAdcional;

    private String identificador;

    @ManyToOne
    @JoinColumn(name = "estado_emision_id",nullable = false)
    private EstadoEmision estadoEmision;

}
