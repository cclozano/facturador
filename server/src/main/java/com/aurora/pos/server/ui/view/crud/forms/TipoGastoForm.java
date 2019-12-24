package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.impuestos.entidades.TipoFacturaCompra;
import com.aurora.impuestos.entidades.TipoGasto;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;


@ViewScope
@SpringComponent
public class TipoGastoForm extends BaseFormEntity<TipoFacturaCompra> {


    private MTextField nombre = new MTextField("Nombre:").withWidth("100%").withWidth("100%");
    private MTextField detalle = new MTextField("Detalle:").withWidth("100%").withWidth("100%");
    private ComboBox<TipoGasto> tipoGasto = new ComboBox<>("Tipo Gasto:");

    public TipoGastoForm() {
        super(TipoFacturaCompra.class);
        setModalWidth("60%");
        setModalWindowTitle("Tipo Gasto");
    }

    @Override
    protected Component createContent() {
        tipoGasto.setWidth("100%");
        tipoGasto.setItems(TipoGasto.values());
        return getDefaultLayoutContent(new MFormLayout(nombre,detalle,tipoGasto).withWidth("100%"));
    }
}
