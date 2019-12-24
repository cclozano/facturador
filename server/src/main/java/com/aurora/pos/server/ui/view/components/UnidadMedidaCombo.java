package com.aurora.pos.server.ui.view.components;


import com.aurora.inventario.entidades.UnidadMedida;
import com.aurora.inventario.repositorios.UnidadMedidaRepositorio;
import com.vaadin.spring.annotation.SpringComponent;

import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

@SpringComponent
@PrototypeScope
public class UnidadMedidaCombo extends ComboBox<UnidadMedida> {


    private UnidadMedidaRepositorio unidadMedidaRepositorio;

    @Autowired
    public UnidadMedidaCombo(UnidadMedidaRepositorio unidadMedidaRepositorio) {
        this.unidadMedidaRepositorio = unidadMedidaRepositorio;
        setItems(unidadMedidaRepositorio.findAll());
        setItemCaptionGenerator(unidadMedida -> unidadMedida.getNombre());

        this.setWidth("100%");

    }
}
