package com.aurora.pos.server.ui.view.components;

import com.aurora.inventario.entidades.Item;
import com.aurora.pos.entidades.DetalleListaPrecios;
import com.aurora.pos.entidades.ListaPrecios;
import com.vaadin.ui.ComboBox;

import java.util.List;

public class ItemPrecioCombo extends ComboBox<DetalleListaPrecios> {


    public ItemPrecioCombo()
    {
        this.setWidth("100%");
        this.setItemCaptionGenerator(item ->item!=null?item.getDescripcion():"");
    }
}
