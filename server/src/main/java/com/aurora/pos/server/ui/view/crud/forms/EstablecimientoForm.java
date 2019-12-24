package com.aurora.pos.server.ui.view.crud.forms;


import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Establecimiento;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;


@ViewScope
@SpringComponent
public class EstablecimientoForm extends BaseFormEntity<Establecimiento> {


    private MTextField codigo = new MTextField("Codigo:").withWidth("100%");
    private MTextField nombreComercial= new MTextField("Nombre Comercial:").withWidth("100%");
    private MTextField nombreCorto= new MTextField("Nombre Corto:").withWidth("100%");
    private TextArea direccion = new TextArea("Direccion:");

    public EstablecimientoForm() {
        super(Establecimiento.class);
        setModalWindowTitle("Establecimiento");
        setModalWidth("50%");
    }

    @Override
    protected Component createContent() {
        direccion.setWidth("100%");
        return getDefaultLayoutContent(new MFormLayout(codigo,nombreComercial,nombreCorto,direccion).withWidth("100%"));
    }
}
