package com.aurora.pos.server.ui.view;

import com.aurora.pos.sri.esquemas.factura_v1.Factura;

public class ConsultaFacturaModifiedEvent {

    Factura factura;
    public ConsultaFacturaModifiedEvent(Factura factura) {
        this.factura = factura;
    }

    public Factura getFactura() {
        return factura;
    }
}
