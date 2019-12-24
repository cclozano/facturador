package com.aurora.pos.sri.api;

import com.aurora.pos.entidades.MensajeAutorizacion;
import com.aurora.pos.entidades.MensajeRespuesta;
import com.aurora.pos.entidades.RespuestaAutorizacion;
import com.aurora.pos.entidades.RespuestaRecepcion;
import com.aurora.pos.sri.autorizacion.AutorizacionComprobantesOfflineServiceSoapBinding;
import com.aurora.pos.sri.autorizacion.autorizacion;
import com.aurora.pos.sri.autorizacion.mensaje;
import com.aurora.pos.sri.autorizacion.respuestaComprobante;
import com.aurora.pos.sri.recepcion.RecepcionComprobantesOfflineServiceSoapBinding;
import com.aurora.pos.sri.recepcion.comprobante;
import com.aurora.pos.sri.recepcion.respuestaSolicitud;

import java.util.ArrayList;

/**
 * Created by max on 14/06/17.
 */
public class AutorizadorImp implements Autorizador {

    RecepcionComprobantesOfflineServiceSoapBinding recepcionService; //= new RecepcionComprobantesOfflineServiceSoapBinding();

    AutorizacionComprobantesOfflineServiceSoapBinding autorizacionService; //= new AutorizacionComprobantesOfflineServiceSoapBinding();

    private static final String CODIGO_CLAVE_REGISTRADA="43";


    public  AutorizadorImp(String urlRecepcion,String urlAutorizacion)
    {
        recepcionService =  new RecepcionComprobantesOfflineServiceSoapBinding(urlRecepcion);
        autorizacionService = new AutorizacionComprobantesOfflineServiceSoapBinding(urlAutorizacion);

    }


    @Override
    public RespuestaAutorizacion consultarAutorizacion(String codigo) {
        try {
            respuestaComprobante resp = autorizacionService.autorizacionComprobante(codigo);

            if(resp!=null&&resp.autorizaciones!=null&&resp.autorizaciones.size()>0)
            {
               autorizacion aut =  resp.autorizaciones.get(0);
               RespuestaAutorizacion respuestaAutorizacion = new RespuestaAutorizacion();
               respuestaAutorizacion.setEstado(aut.estado);
               respuestaAutorizacion.setAmbiente(aut.ambiente);
               respuestaAutorizacion.setComprobante(aut.comprobante);
               respuestaAutorizacion.setFechaAutorizacion(aut.fechaAutorizacion);
               respuestaAutorizacion.setNumeroAutorizacion(aut.numeroAutorizacion);
               respuestaAutorizacion.setMensajes(new ArrayList<>());
              for(mensaje item : aut.mensajes)
              {
                  MensajeAutorizacion xxx = new MensajeAutorizacion();
                  xxx.setIdentificador(item.identificador);
                  xxx.setInformacionAdicional(item.informacionAdicional);
                  xxx.setMensaje(item.mensaje);
                  xxx.setTipo(item.tipo);
                  xxx.setRespuesta(respuestaAutorizacion);
                  respuestaAutorizacion.getMensajes().add(xxx);
              }
               return  respuestaAutorizacion;
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RespuestaRecepcion enviarDocumento(byte[] documento) throws Exception {

        respuestaSolicitud resp = recepcionService.validarComprobante(documento);
        RespuestaRecepcion recepcion = new RespuestaRecepcion();
        recepcion.setEstado(resp.estado);
        for (comprobante item : resp.comprobantes) {
            recepcion.setClaveAcceso(item.claveAcceso);
            for (com.aurora.pos.sri.recepcion.mensaje mensaje : item.mensajes) {
                MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                mensajeRespuesta.setClaveAcceso(item.claveAcceso);
                mensajeRespuesta.setMensaje(mensaje.mensaje);
                mensajeRespuesta.setInformacionAdiconal(mensaje.informacionAdicional);
                mensajeRespuesta.setCodigo(mensaje.identificador);
                recepcion.getMensajes().add(mensajeRespuesta);
                mensajeRespuesta.setRespuesta(recepcion);
                if(mensajeRespuesta.getCodigo().equals(CODIGO_CLAVE_REGISTRADA))
                    recepcion.setEstado("DEVUELTA_CLAVE_REGISTRADA");
            }
        }
        return recepcion;
    }
}
