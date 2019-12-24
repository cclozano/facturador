package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by max on 10/08/17.
 */
@Entity
@Setter @Getter
public class Ciudad extends BaseEntity{

    private String nombre;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROVINCIA_ID")
    private Provincia provincia;
}
