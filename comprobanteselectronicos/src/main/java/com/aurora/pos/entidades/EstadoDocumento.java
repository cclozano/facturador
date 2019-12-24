package com.aurora.pos.entidades;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EstadoDocumento {
    private String claveAcceso;
    private RespuestaRecepcion  respuestaRecepcion;
    private RespuestaAutorizacion respuestaAutorizacion;
}
