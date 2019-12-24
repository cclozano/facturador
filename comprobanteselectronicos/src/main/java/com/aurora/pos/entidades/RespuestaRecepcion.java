package com.aurora.pos.entidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class RespuestaRecepcion {
    private String estado;
    private String claveAcceso;
    private Collection<MensajeRespuesta> mensajes = new ArrayList<>();
}
