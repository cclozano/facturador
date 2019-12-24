package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.impuestos.entidades.RetencionImpuesto;

public class ConsultaRetencionImpuestoEvent {
    RetencionImpuesto retencionImpuesto;
    public ConsultaRetencionImpuestoEvent(RetencionImpuesto retencionImpuesto) {
        this.retencionImpuesto = retencionImpuesto;
    }

    public RetencionImpuesto getRetencion() {
        return retencionImpuesto;
    }
}
