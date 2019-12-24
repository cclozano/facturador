package com.aurora.pos.sri.api;

import com.aurora.pos.entidades.DocumentoElectronico;
import com.aurora.pos.entidades.EstadoDocumento;
import com.aurora.pos.entidades.RespuestaAutorizacion;
import com.aurora.pos.entidades.RespuestaRecepcion;
import com.aurora.pos.sri.esquemas.XMLSerializador;
import com.aurora.pos.sri.firma.FirmaElectronica;
import com.aurora.pos.sri.mappers.Mapper;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProcesadorDocumentoImp<Comprobante, Doc extends DocumentoElectronico> implements ProcesadorDocumento<Doc>{

    Mapper<Doc,Comprobante> mapper;
    XMLSerializador<Comprobante> xmlSerializador;
    Autorizador autorizador;
    FirmaElectronica firmaElectronica;

    public ProcesadorDocumentoImp(Mapper<Doc,Comprobante> mapper,
                                  XMLSerializador<Comprobante> xmlSerializador,
                                  Autorizador autorizador,FirmaElectronica firmaElectronica)
    {
        this.mapper = mapper;
        this.xmlSerializador = xmlSerializador;
        this.autorizador = autorizador;
        this.firmaElectronica = firmaElectronica;
    }



    @Override
    public EstadoDocumento procesar(Doc documento) throws Exception {

        EstadoDocumento estadoDocumento = new EstadoDocumento();

        estadoDocumento.setClaveAcceso(documento.getClaveAcceso());
        RespuestaRecepcion respuestaRecepcion = enviar(documento);
        estadoDocumento.setRespuestaRecepcion(respuestaRecepcion);

        if(respuestaRecepcion.getEstado().equals("RECIBIDA") || respuestaRecepcion.getEstado().equals("DEVUELTA_CLAVE_REGISTRADA")) {
            Thread.sleep(5000);
            estadoDocumento.setRespuestaAutorizacion(consultarAutorizacion(documento.getClaveAcceso()));
        }
        return estadoDocumento;
    }


    public RespuestaRecepcion enviar(Doc documentoElectronico) throws Exception {
        Comprobante comprobante =  mapper.map(documentoElectronico);
        ByteArrayOutputStream baos = xmlSerializador.serializar(comprobante);
        byte[] firmado = firmaElectronica.firmar(baos);
        return autorizador.enviarDocumento(firmado);
    }


    public RespuestaAutorizacion consultarAutorizacion(String claveAcceso)
    {
        return this.autorizador.consultarAutorizacion(claveAcceso);
    }
}
