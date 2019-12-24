package com.aurora.pos.server.ui.view.facturar;

import com.aurora.pos.entidades.DetalleFactura;
import com.aurora.pos.entidades.DetalleListaPrecios;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.server.ui.view.components.ItemPrecioCombo;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.grid.MGrid;

import java.math.BigDecimal;
import java.util.List;


public class DetalleFacturaGrid extends MGrid<DetalleFactura> {


    private Factura factura;

    private ItemPrecioCombo itemsCombo;

    private TextField txtDescripcion = new TextField();
    private TextField txtCantidad = new TextField();
    private TextField txtPrecio = new TextField();
    private TextField txtDescuento = new TextField();

    private FooterCell subTotalColumn;
    private FooterCell iceColumn;
    private FooterCell ivaColumn;
    private FooterCell totalColumn;





    public DetalleFacturaGrid(ListaPrecios listaPrecios)
    {
        super(DetalleFactura.class);
        this.itemsCombo = new ItemPrecioCombo();
        this.itemsCombo.setItems(listaPrecios.getDetalles());


        removeAllColumns();
        Column<DetalleFactura, String> itemColumn =  addColumn(detalleFactura -> detalleFactura.getItem()!=null
                ?detalleFactura.getItem().getNombreCompleto():"")
                .setCaption("Item").setExpandRatio(1).setId("itemColumn");
        Column<DetalleFactura, String> descripcionColumn  = addColumn(detalleFactura -> detalleFactura.getDescripcion())
                .setCaption("Descripcion").setExpandRatio(1).setId("descripcionColumn");
        Column<DetalleFactura, BigDecimal> cantidadColumn = addColumn(detalleFactura -> detalleFactura.getCantidad())
                .setCaption("Cantidad").setId("cantidadColumn");
        addColumn(detalleFactura -> detalleFactura.getUnidadMedida()!=null?detalleFactura.getUnidadMedida().getNombre():"").setCaption("U. Medida");
        Column<DetalleFactura, BigDecimal> precioColumn = addColumn(detalleFactura ->
                detalleFactura.getPrecioUnitario())
                .setCaption("Precio U.").setId("precioColumn");
        Column<DetalleFactura, BigDecimal> descuentoColumn =  addColumn(detalleFactura -> detalleFactura.getDescuento())
                .setCaption("Descuento").setId("descuentoColumn");
        addColumn(detalleFactura -> detalleFactura.getSubTotal()).setCaption("Sub Total").setId("subTotalColumn");

        addComponentColumn(detalleFactura -> {
            Button borrarDetalleButton = new Button(FontAwesome.TRASH);
            borrarDetalleButton.setStyleName(ValoTheme.BUTTON_LINK);
            borrarDetalleButton.addClickListener(clickEvent -> {
                this.factura.getDetalles().remove(detalleFactura);
                this.setRows(this.getDetallesFactura());
                updateFooter();

            });

            return borrarDetalleButton;
        }).setId("borrarColumn").setCaption("Borrar");

        this.setWidth("100%");
        this.setHeight("100%");
        getEditor().setEnabled(true);
        getEditor().setCancelCaption("Cancelar");
        getEditor().setSaveCaption("Guardar");

        Binder<DetalleFactura> binder = this.getEditor().getBinder();

        Binder.Binding<DetalleFactura, DetalleListaPrecios> binding = binder.forField(this.itemsCombo)
                .bind(DetalleFactura::getDetalleListaPrecios, DetalleFactura::setDetalleListaPrecios);

        itemColumn.setEditorBinding(binding);

        descripcionColumn.setEditorBinding(binder.bind(txtDescripcion,DetalleFactura::getDescripcion,DetalleFactura::setDescripcion));
        cantidadColumn.setEditorBinding(binder.forField(txtCantidad)
                .withConverter(new StringToBigDecimalConverter("Solo numeros"))
                .withValidator((numero,context) -> new ValidationResult() {
                    @Override
                    public String getErrorMessage() {
                        return "Debe ser mayor a cero";
                    }

                    @Override
                    public boolean isError() {
                        return numero.compareTo(BigDecimal.ZERO)==-1 || numero.compareTo(BigDecimal.ZERO)==0;
                    }
                }).bind(DetalleFactura::getCantidad,DetalleFactura::setCantidad));
        precioColumn.setEditorBinding(binder.forField(txtPrecio)
                .withConverter(new StringToBigDecimalConverter("Solo numeros")). bind(DetalleFactura::getPrecioUnitario,DetalleFactura::setPrecioUnitario));
        descuentoColumn.setEditorBinding(binder.forField(txtDescuento)
                .withConverter(new StringToBigDecimalConverter("Solo numeros")). bind(DetalleFactura::getDescuento,DetalleFactura::setDescuento));


        itemsCombo.addValueChangeListener(valueChangeEvent -> {
           if(itemsCombo.getValue()!=null && itemsCombo.getValue().getPrecio()!=null)
               txtPrecio.setValue(itemsCombo.getValue().getPrecio().toString());
        });


        FooterRow groupingTotal = this.prependFooterRow();
        groupingTotal.join(
                groupingTotal.getCell("itemColumn"),
                groupingTotal.getCell("descripcionColumn"),
                groupingTotal.getCell("cantidadColumn"),
                groupingTotal.getCell("precioColumn")
        );

        totalColumn = groupingTotal.join(groupingTotal.getCell("subTotalColumn"),groupingTotal.getCell("borrarColumn"));
        groupingTotal.getCell("descuentoColumn").setText("Total");
        totalColumn.setText("$ 0");

        FooterRow groupingIva = this.prependFooterRow();
        groupingIva.join(
                groupingIva.getCell("itemColumn"),
                groupingIva.getCell("descripcionColumn"),
                groupingIva.getCell("cantidadColumn"),
                groupingIva.getCell("precioColumn")
        );
        groupingIva.getCell("descuentoColumn").setText("IVA");
        ivaColumn = groupingIva.join(groupingTotal.getCell("subTotalColumn"),groupingTotal.getCell("borrarColumn"));
        ivaColumn.setText("$ 0");


        FooterRow groupingIce = this.prependFooterRow();
        groupingIce.join(
                groupingIce.getCell("itemColumn"),
                groupingIce.getCell("descripcionColumn"),
                groupingIce.getCell("cantidadColumn"),
                groupingIce.getCell("precioColumn")
        );
        groupingIce.getCell("descuentoColumn").setText("ICE");
        iceColumn = groupingIce.join(groupingTotal.getCell("subTotalColumn"),groupingTotal.getCell("borrarColumn"));
        iceColumn.setText("$ 0");

        FooterRow groupingSubtotal = this.prependFooterRow();
        groupingSubtotal.join(
                groupingSubtotal.getCell("itemColumn"),
                groupingSubtotal.getCell("descripcionColumn"),
                groupingSubtotal.getCell("cantidadColumn"),
                groupingSubtotal.getCell("precioColumn")
        );
        groupingSubtotal.getCell("descuentoColumn").setText("Sub Total");
        subTotalColumn = groupingSubtotal.join(groupingTotal.getCell("subTotalColumn"),groupingTotal.getCell("borrarColumn"));
        subTotalColumn.setText("$ 0");

        getEditor().addSaveListener(editorSaveEvent -> {
           updateFooter();
        });

    }

    public void updateFooter()
    {
        subTotalColumn.setText("$ "+this.factura.getTotalSinImpuestos().toString());
        ivaColumn.setText("$" + this.factura.getTotalIva12().toString());
        iceColumn.setText("$ "+this.factura.getTotalIce());
        totalColumn.setText("$ " + this.factura.getTotalFactura());
    }




    public void setFactura(Factura factura)
    {
        this.factura = factura;
        this.setRows(this.factura.getDetalles());
        updateFooter();
    }

    public void setListaPrecios(ListaPrecios listaPrecios)
    {
        this.itemsCombo.setItems(listaPrecios.getDetalles());
    }



    public List<DetalleFactura> getDetallesFactura() {
        return factura.getDetalles();
    }
}
