package com.aurora.pos.entidades;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by max on 19/06/17.
 */

@Getter @Setter
public class MensajeAutorizacion {
    private String identificador;
    private String mensaje;
    private String informacionAdicional;
    private String tipo;
    private RespuestaAutorizacion  respuesta ;
}
