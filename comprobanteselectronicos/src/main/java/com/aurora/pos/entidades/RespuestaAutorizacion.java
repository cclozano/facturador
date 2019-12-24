package com.aurora.pos.entidades;


import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by max on 14/06/17.
 */
@Getter @Setter
public class RespuestaAutorizacion {

    private String estado;
    private Date fechaAutorizacion;
    private String ambiente;
    private String numeroAutorizacion;
    private String comprobante;
    private Collection<MensajeAutorizacion> mensajes = new ArrayList<>();
}
