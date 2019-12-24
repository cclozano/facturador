package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class TarjetaCredito extends BaseEntity{

    @Size(min = 1,max = 25)
    @Column(unique = true,length = 25,nullable = false)
    private String codigo;

    @Size(min = 4,max = 50)
    @Column(unique = true,length = 50,nullable = false)
    private String nombre;

    @JoinColumn(name = "banco_emisor",nullable = false)
    @ManyToOne
    private Banco bancoEmisor;


}
