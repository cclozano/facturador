package com.aurora.pos.server.ui.view.crud.forms;


import com.aurora.framework.vaadinext.ToolBar;
import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.inventario.repositorios.UnidadMedidaRepositorio;
import com.aurora.pos.entidades.DetalleListaPrecios;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.server.ui.view.components.DetallePreciosGrid;
import com.aurora.pos.server.ui.view.components.ItemsCombo;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.DateToLongConverter;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritin.v7.fields.MDateField;
import org.vaadin.viritin.v7.fields.MTextArea;
import org.vaadin.viritin.v7.fields.MTextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ViewScope
@SpringComponent
public class ListaPreciosForm extends BaseFormEntity<ListaPrecios> {

    private DateField fechaElaboracion = new DateField("Fecha:");
    private TextField nombreCorto = new TextField("Nombre Corto:");
    private TextArea descripcion = new TextArea("Descripcion");






    @Autowired
    private DetallePreciosGrid detalles;




    public ListaPreciosForm() {
        super(ListaPrecios.class);
        //setModalHeight("90%");
        setModalWidth("90%");
        setModalWindowTitle("Lista de Precios");

        fechaElaboracion.setWidth("50%");
        nombreCorto.setWidth("50%");
        descripcion.setWidth("100%");


    }

    @Override
    public void bind()
    {

        getBinder().forField(fechaElaboracion)
                .withConverter(new LocalDateToDateConverter())
                .bind("fechaElaboracion");
        fechaElaboracion.setValue(LocalDate.now());
        //getBinder().bind("nombr")
        super.bind();
        detalles.getEditor().addOpenListener(editorOpenEvent -> {
            boolean valid = getBinder().isValid();
            this.getSaveButton().setEnabled(valid);
        });

    }

    @Override
    public void setEntity(ListaPrecios entity)
    {
        super.setEntity(entity);
        detalles.setItems((List<DetalleListaPrecios>)entity.getDetalles());

    }


    private TabSheet tabSheet = new TabSheet();

    private ToolBar toolBar = new ToolBar();
    private Button addItem = new Button(VaadinIcons.PLUS_CIRCLE);
    private Button removeItem = new Button(VaadinIcons.TRASH);




    @Override
    protected Component createContent() {

        removeItem.setEnabled(false);
        detalles.addSelectionListener(selectionEvent -> removeItem.setEnabled(!detalles.asSingleSelect().isEmpty()));
        detalles.setWidth("100%");
        detalles.setHeight("100%");
        addItem.addClickListener(clickEvent -> {
            if(!detalles.isAsignable())
            {
                Notification.show("Todos los productos estan presentes en la lista", Notification.Type.TRAY_NOTIFICATION);
                return;
            }
            getEntity().agregarDetalle(new DetalleListaPrecios());
            detalles.setItems((List<DetalleListaPrecios>) getEntity().getDetalles());
            this.getSaveButton().setEnabled(true);
        });

        removeItem.addClickListener(clickEvent -> {
            getEntity().getDetalles().remove(detalles.asSingleSelect().getValue());
            detalles.setItems((List<DetalleListaPrecios>) getEntity().getDetalles());
        });

        toolBar.addDerecha(addItem);
        toolBar.addDerecha(removeItem);
        tabSheet.setWidth("100%");
        tabSheet.setHeight("100%");
        tabSheet.addTab(new MFormLayout(fechaElaboracion,nombreCorto,descripcion).withWidth("50%").withHeight("100%"),"Informacion General");
        tabSheet.addTab(new MVerticalLayout(toolBar,detalles).withWidth("100%").withHeight("100%").expand(detalles),"Precios");
        return new MVerticalLayout(tabSheet,getToolbar()).withWidth("100%").withHeight("100%").expand(tabSheet);
       //return tabSheet;
    }




}
