package com.aurora.impuestos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter @Setter
@MappedSuperclass
public abstract class  ComprobanteBase extends BaseEntity{

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public String xml;

    @Column(name = "clave_acceso")
    @Size(max = 50)
    private String claveAcceso;


    @Temporal(value = TemporalType.DATE)
    @NotNull
    private Date fecha;

    private String pathFoto;




}
