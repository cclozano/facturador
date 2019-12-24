package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Ciudad;
import com.aurora.pos.entidades.Provincia;
import com.aurora.pos.repositorios.CiudadRepositorio;
import com.aurora.pos.repositorios.ProvinciaRepositorio;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;


@ViewScope
@SpringComponent
public class CiudadForm extends BaseFormEntity<Ciudad> {


    private MTextField nombre = new MTextField("Nombre").withWidth("100%");
    private ComboBox<Provincia> provincia = new ComboBox<>("Provincia");

    @Autowired
    private CiudadRepositorio repo;

    @Autowired
    private ProvinciaRepositorio provinciaRepositorio;

    public CiudadForm() {
        super(Ciudad.class);
        setModalWidth("60%");

        setModalWindowTitle("Ciudad");
    }

    @Override
    protected Component createContent() {
        provincia.setWidth("100%");
        provincia.setItems(provinciaRepositorio.findAll());
        provincia.setItemCaptionGenerator(prop -> prop.getNombre());
        nombre.setWidth("100%");

        return getDefaultLayoutContent(new MFormLayout(
                nombre,
                provincia
        ).withWidth("100%"));
    }


}
