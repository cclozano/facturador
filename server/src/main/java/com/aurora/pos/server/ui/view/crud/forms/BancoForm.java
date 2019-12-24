package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Banco;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

@ViewScope
@SpringComponent
public class BancoForm extends BaseFormEntity<Banco> {

    private TextField codigo = new MTextField("Codigo:").withWidth("100%");
    private TextField nombre = new MTextField("Nombre:").withWidth("100%");



    public BancoForm() {
        super(Banco.class);
        setModalWindowTitle("Banco");
        setModalWidth("50%");
        setModalHeight("-1px");
    }

    @Override
    protected Component createContent() {
        return getDefaultLayoutContent(new MFormLayout(
                codigo,
                nombre
        ).withWidth("100%"));
    }
}
