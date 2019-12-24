package com.aurora.pos.servicios.imp;

import com.aurora.pos.entidades.EstadoDocumento;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.entidades.MensajeRespuesta;
import com.aurora.pos.entidades.RespuestaRecepcion;
import com.aurora.pos.sri.api.ProcesadorDocumento;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServicioFacturaImpTest {

    @Mock
    private ProcesadorDocumento<Factura> procesadorDocumento;

    @Before
    public void init() throws Exception {

        EstadoDocumento estadoDocumento = new EstadoDocumento();
        estadoDocumento.setClaveAcceso("1234567896329");

        RespuestaRecepcion respuestaRecepcion = new RespuestaRecepcion();
        respuestaRecepcion.setEstado("RECIBIDA");
        respuestaRecepcion.setClaveAcceso("1234567896329");

        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
        mensajeRespuesta.setCodigo("31");
        mensajeRespuesta.setMensaje("abc");
        mensajeRespuesta.setInformacionAdiconal("xyz");
        List<MensajeRespuesta> mesajesRespuesta = new ArrayList<>();
        mesajesRespuesta.add(mensajeRespuesta);
        respuestaRecepcion.setMensajes(mesajesRespuesta);

        //estadoDocumento.setRespuestaRecepcion();


       // when(procesadorDocumento.procesar(any(Factura.class))).then()
    }

    @Test
    public void facturar() throws Exception {
        assertNotNull(procesadorDocumento);
    }

    @Test
    public void autorizarFactura() throws Exception {
    }

}