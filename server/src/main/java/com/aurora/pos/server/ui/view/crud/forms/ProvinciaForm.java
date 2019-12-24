package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Provincia;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

/**
 * Created by max on 10/08/17.
 */

@ViewScope
@SpringComponent
public class ProvinciaForm extends BaseFormEntity<Provincia> {

    private static final long serialVersionUID = 1L;

    MTextField nombre  = new MTextField("Nombre").withWidth("100%");

    public ProvinciaForm() {
        super(Provincia.class);
        setModalWindowTitle("Provincia");
        setModalWidth("50%");

    }


    @Override
    protected Component createContent() {
        return getDefaultLayoutContent(new MFormLayout(nombre).withWidth("100%"));
    }


}
