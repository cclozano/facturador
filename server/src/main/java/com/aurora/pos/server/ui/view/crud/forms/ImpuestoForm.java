package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Impuesto;
import com.aurora.pos.repositorios.ImpuestoRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;

@ViewScope
@SpringComponent
public class ImpuestoForm extends BaseFormEntity<Impuesto> {

    private MTextField codigo = new MTextField("Codigo:").withWidth("100%");
    private MTextField tarifa= new MTextField("Tarifa:").withWidth("100%");
    private MTextField nombre= new MTextField("Nombre:").withWidth("100%");
    private MTextField codigoPorcentaje = new MTextField("Codigo Porcentaje:").withWidth("100%");
    private TextArea descripcion = new TextArea("Descripcion:");

    @Autowired
    private ImpuestoRepositorio impuestoRepositorio;

    public ImpuestoForm() {
        super(Impuesto.class);
        setModalWindowTitle("Impuesto");
        setModalWidth("50%");
    }


    @Override
    protected Component createContent() {
        descripcion.setWidth("100%");
        FormLayout form = new FormLayout(nombre, descripcion,codigo,codigoPorcentaje, tarifa,getToolbar());
        form.setMargin(true);
        return form;
    }
}
