package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.util.Identificacion;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by max on 09/08/17.
 */
@Entity
@Getter
@Setter
public class Cliente extends BaseEntity{

    @NotNull(message = "Nombre es requerido")
    private String nombre;
    @NotNull(message = "Direccion es requerido")
    private String direccion;
    @NotNull(message = "Direccion es requerido")
    private String telefono;
    @NotNull(message = "Identificacion es requerido")
   // @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Solo Numeros")
    @Identificacion(message = "No es una identificacion valida")
    private String identificacion;
    @NotNull(message = "Correo es requerido")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Ingrese un correo valido")
    private String correo;

    @NotNull
    @Column(name = "tipo_identificacion")
    @Enumerated(value = EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;

}
