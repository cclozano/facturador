package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Entity
@Getter @Setter
public class Banco extends BaseEntity {

    @NotNull
    @Size(min = 1,max = 15)
    @Column(length = 15)
    private String codigo;
    @Size(min = 4,max = 50)
    @NotNull
    @Column(length = 50)
    private String nombre;
}
