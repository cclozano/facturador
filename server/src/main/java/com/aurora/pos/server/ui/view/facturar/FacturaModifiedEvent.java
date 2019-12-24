package com.aurora.pos.server.ui.view.facturar;

import com.aurora.framework.vaadinext.crud.EntityModifiedEvent;
import com.aurora.pos.entidades.Factura;

public class FacturaModifiedEvent extends EntityModifiedEvent<Factura> {
    public FacturaModifiedEvent(Factura factura) {
        super(factura);
    }
}
