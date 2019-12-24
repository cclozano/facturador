package com.aurora.pos.servicios;

import com.aurora.pos.entidades.EstadoDocumento;
import com.aurora.pos.entidades.EstadoEmision;
import com.aurora.pos.entidades.Factura;

public interface ServicioFacturacion {
    void guardar(Factura factura) throws ServiceException;
    Factura autorizarFactura(Factura factura) throws Exception;
}
