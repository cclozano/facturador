package com.aurora.pos.entidades;


import com.aurora.framework.data.BaseEntity;
import com.aurora.util.Identificacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Entity
public class Emisor extends BaseEntity{


    @NotNull
    private Ambiente ambiente = Ambiente.DESARROLLO;
    private int numeroUltimaFacturaDesarrollo = 0;
    private int numeroContribuyenteEspecial = 0;
    private boolean obligadoContabilidad = false;

    @NotNull
    private String razonSocialEmisor;// = "CARLOS ALBERTO MENDOZA MIELES";
    @NotNull
    private String nombreComercialEmisor;// = "CARLOS ALBERTO MENDOZA MIELES";
    @Identificacion
    //@NotNull
    @Size(min = 13)
    private String rucEmisor;// = "1309410445001";
    @NotNull
    @Size(min = 1)
    private String direccionMatriz;// = "Bellavista Mz 35 Villa 10";

    @NotNull
    @Size(min = 6)
    @Column(name = "path_logo")
    private String pathLogo;

    @NotNull
    @Size(min = 6)
    @Column(name = "path_hey")
    private String pathkey;

    @NotNull
    @Size(min = 1, message = "Ingrese la clave del token")
    @Column(name = "password_key")
    private String passwordKey;


    @Embedded
    private CorreoEmisorConfig correo = new CorreoEmisorConfig();

    public enum Ambiente
    {
        DESARROLLO(1),
        PRODUCCION(2);

        private int value;

        Ambiente(int value) {
            this.value = value;
        }

        public int getValue()
        {
            return this.value;
        }
    }

    public Long getSiguienteNumeroDesarrollo()
    {
        return (long) (numeroUltimaFacturaDesarrollo + 1);
    }


}
