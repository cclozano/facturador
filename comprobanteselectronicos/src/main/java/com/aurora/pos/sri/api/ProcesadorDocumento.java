package com.aurora.pos.sri.api;

import com.aurora.pos.entidades.DocumentoElectronico;
import com.aurora.pos.entidades.EstadoDocumento;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProcesadorDocumento<Documento extends DocumentoElectronico> {
    EstadoDocumento procesar(Documento documento) throws Exception;
}
