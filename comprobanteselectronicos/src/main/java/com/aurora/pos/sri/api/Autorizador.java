package com.aurora.pos.sri.api;


import com.aurora.pos.entidades.RespuestaAutorizacion;
import com.aurora.pos.entidades.RespuestaRecepcion;

/**
 * Created by max on 14/06/17.
 */
public interface Autorizador {

    RespuestaAutorizacion consultarAutorizacion(String codigo);
    RespuestaRecepcion enviarDocumento(byte[] documento) throws Exception;
}
