package com.aurora.inventario.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.inventario.entidades.UnidadMedida;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;



@PrototypeScope
@SpringComponent
public class UnidadMedidaForm extends BaseFormEntity<UnidadMedida>{

    private MTextField codigo = new MTextField("Codigo:").withWidth("100%");
    private MTextField nombre =  new MTextField("Nombre:").withWidth("100%");


    public UnidadMedidaForm() {
        super(UnidadMedida.class);
        setModalWindowTitle("Unidad Medida");
        setModalWidth("50%");
    }

    @Override
    protected Component createContent() {
        return getDefaultLayoutContent(new MFormLayout(codigo,nombre).withWidth("100%"));
    }
}
