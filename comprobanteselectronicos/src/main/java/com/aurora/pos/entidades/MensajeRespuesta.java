package com.aurora.pos.entidades;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by max on 15/06/17.
 */
@Getter @Setter
public class MensajeRespuesta {
    private String claveAcceso;
    private String codigo;
    private String mensaje;
    private String informacionAdiconal;
    private String identificador;
    private RespuestaRecepcion respuesta;

}
