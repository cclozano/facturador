package com.aurora.pos.server.ui.view.facturar;

import com.aurora.framework.vaadinext.ToolBar;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.repositorios.ListaPreciosRepositorio;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.viritin.layouts.MVerticalLayout;


public class SeleccionarListaPreciosWindows extends Window
{
    ComboBox<ListaPrecios> comboBoxListaPrecios;
    Button aceptarButton = new Button("Seleccionar");
    Button cancelarButton = new Button("Cancelar");


    ListaPreciosRepositorio listaPreciosRepositorio;


    SeleccionarListaPreciosWindows(ListaPreciosRepositorio repositorio)
    {
        listaPreciosRepositorio = repositorio;
        comboBoxListaPrecios = new ComboBox<>();
        comboBoxListaPrecios.setTextInputAllowed(false);
        comboBoxListaPrecios.setItemCaptionGenerator(listaPrecios -> listaPrecios.getNombreCorto());
        comboBoxListaPrecios.setItems(listaPreciosRepositorio.findAll());

        comboBoxListaPrecios.setWidth("100%");
        comboBoxListaPrecios.setEmptySelectionAllowed(false);
        //comboBoxListaPrecios.setEmptySelectionCaption("Seleccione un lista de precios");
        ToolBar toolBar = new ToolBar();
        toolBar.addDerecha(aceptarButton);
        toolBar.addDerecha(cancelarButton);
        aceptarButton.setEnabled(false);
        aceptarButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancelarButton.addClickListener(clickEvent -> UI.getCurrent().removeWindow(this));
        comboBoxListaPrecios.addValueChangeListener(valueChangeEvent ->
                aceptarButton.setEnabled(comboBoxListaPrecios.getValue()!=null));

        this.center();
        this.setModal(true);
        this.setWidth("50%");
        this.setCaption("Seleccione una lista de productos");

        MVerticalLayout layout = new MVerticalLayout(comboBoxListaPrecios,toolBar)
                .withMargin(true).withSpacing(true);
        this.setContent(layout);
    }

    void openInModalPopPup()
    {
        UI.getCurrent().addWindow(this);
    }




}