package com.aurora.pos.server.ui.view.components;

import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.entidades.UnidadMedida;
import com.aurora.inventario.repositorios.UnidadMedidaRepositorio;
import com.aurora.pos.entidades.DetalleListaPrecios;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.grid.MGrid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringComponent
@PrototypeScope
public class DetallePreciosGrid extends MGrid<DetalleListaPrecios> {

    private TextField txtPrecio = new TextField();

    private ItemsCombo itemsCombo;

    ListDataProvider<DetalleListaPrecios> dataProvider = new ListDataProvider<>(new ArrayList<>());



    private UnidadMedidaCombo unidadMedidaCombo;



    @Autowired
    public DetallePreciosGrid(ItemsCombo itemsCombo,UnidadMedidaCombo unidadMedidaCombo) {
        super(DetalleListaPrecios.class);
        this.itemsCombo = itemsCombo;
        this.unidadMedidaCombo = unidadMedidaCombo;
        withProperties("id").withColumnHeaders("id");
        Binder<DetalleListaPrecios> binder = this.getEditor().getBinder();


        //Column<DetalleListaPrecios, Long> idColumn = addColumn(det -> det.getId()).setCaption("ID");
        Column<DetalleListaPrecios, String> productoColumn = addColumn(det -> det.getItem() != null ? det.getItem().getNombreCompleto() : "").setCaption("Item").setExpandRatio(3);
        Column<DetalleListaPrecios,String> unidadMedidaColumn = addColumn(detalleListaPrecios ->
                detalleListaPrecios.getUnidadMedida()!=null?detalleListaPrecios.getUnidadMedida().getNombre():"")
                .setCaption("Unidad Medida");
        Column<DetalleListaPrecios, BigDecimal> precioColumn = addColumn(det -> det.getPrecio()).setCaption("Precio").setExpandRatio(2);


        productoColumn.setEditorBinding(binder.bind(this.itemsCombo, DetalleListaPrecios::getItem, DetalleListaPrecios::setItem));
        precioColumn.setEditorBinding(binder.forField(txtPrecio).withConverter(new StringToBigDecimalConverter("Solo Numeros")).bind(DetalleListaPrecios::getPrecio,DetalleListaPrecios::setPrecio));
        unidadMedidaColumn.setEditorBinding(binder.bind(this.unidadMedidaCombo,DetalleListaPrecios::getUnidadMedida,DetalleListaPrecios::setUnidadMedida));
        getEditor().setEnabled(true);
        getEditor().setCancelCaption("Cancelar");
        getEditor().setSaveCaption("Guardar");

        this.setWidth("100%");
        this.setHeight("100%");


    }


    public void setItems(List<DetalleListaPrecios> detalles)
    {
        this.dataProvider =  DataProvider.ofCollection(detalles);
        this.setDataProvider(this.dataProvider);
        itemsCombo.removeItems(getItems());
    }






    public boolean isAsignable()
    {
        for(DetalleListaPrecios det :   dataProvider.getItems() )
        {
            if(det .getItem() ==  null)
                return false;
        }


       return itemsCombo.getDataProvider().size(new Query<>())>0;
    }


    public List<Item> getItems()
    {
       return this.dataProvider.getItems().stream().map(x->x.getItem()).collect(Collectors.toList());

    }
}