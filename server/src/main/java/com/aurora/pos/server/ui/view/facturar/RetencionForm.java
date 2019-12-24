package com.aurora.pos.server.ui.view.facturar;


import com.aurora.framework.vaadinext.ToolBar;
import com.aurora.impuestos.entidades.CodigoImpuestoRetencion;
import com.aurora.impuestos.entidades.DetalleRetencionImpuesto;
import com.aurora.impuestos.entidades.RetencionImpuesto;
import com.aurora.impuestos.repositorios.CodigoImpuestoRetencionRepositorio;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.repositorios.FacturaRepositorio;
import com.aurora.pos.server.ui.view.crud.forms.ConsultaRetencionImpuestoEvent;
import com.aurora.pos.server.ui.view.crud.forms.ConsultaRetencionSri;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;


@ViewScope
@SpringComponent
public class RetencionForm {


    private MTextField claveAcceso = new MTextField().withWidth("100%");
    private MTextField numero = new MTextField("Numero").withWidth("50%");
    private DateField fecha = new DateField("Fecha");
    private MGrid<DetalleRetencionImpuesto> detalles;

    private Button buttonAdd = new Button(FontAwesome.PLUS_CIRCLE);
    private Button buttonRemove = new Button(FontAwesome.TRASH);
    private Button consultarRetencionSri = new Button(FontAwesome.PLUS_CIRCLE);

    private RetencionImpuesto retencion;

    private Factura factura;

    BeanValidationBinder<RetencionImpuesto> binder = new BeanValidationBinder<>(RetencionImpuesto.class);


    private Button buttonAceptar = new MButton("Aceptar").withStyleName(ValoTheme.BUTTON_PRIMARY);
    private Button buttonCancelar = new MButton("Cancelar");



    @Autowired
    private CodigoImpuestoRetencionRepositorio codigoImpuestoRetencionRepositorio;

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private ConsultaRetencionSri consultaRetencionSri;

    @Autowired
    protected EventBus.ViewEventBus eventBus;


    public RetencionForm() {


        buttonAceptar.setEnabled(false);
        this.buttonRemove.setEnabled(false);
        this.buttonRemove.addClickListener(clickEvent -> {
            DetalleRetencionImpuesto detalleRetencionImpuesto =  this.getDetalles().asSingleSelect().getValue();
            this.retencion.getDetalles().remove(detalleRetencionImpuesto);
            this.getDetalles().setRows(this.retencion.getDetalles());
        });

        this.buttonCancelar = new MButton("Cancelar");
        buttonCancelar.addClickListener(clickEvent -> UI.getCurrent().removeWindow(window));

        this.buttonAceptar = new ConfirmButton("Aceptar",
                "Guardar Retencion?", clickEvent -> {
            this.factura.setRetencionImpuesto(this.retencion);
            this.facturaRepositorio.save(factura);
            UI.getCurrent().removeWindow(window);
        });


        buttonAdd.addClickListener(clickEvent -> {
            DetalleRetencionImpuesto detalle = new DetalleRetencionImpuesto();
            detalle.setRetencionImpuesto(this.retencion);
            this.retencion.getDetalles().add(detalle);
            getDetalles().setItems(this.retencion.getDetalles());

            if (binder.isValid())
                buttonAceptar.setEnabled(true);


        });
        this.buttonAdd.setStyleName(ValoTheme.BUTTON_LINK);
        this.buttonRemove.setStyleName(ValoTheme.BUTTON_LINK);

        consultarRetencionSri.addClickListener(clickEvent ->{
            this.consultaRetencionSri.show();
        });

    }


    protected void bind() {

        binder.forField(fecha).withConverter(new LocalDateToDateConverter()).bind("fecha");
        binder.bindInstanceFields(this);

    }


    public void setEntity(Factura factura,RetencionImpuesto retencion) {
        // this.binder = new BeanValidationBinder<>(RetencionImpuesto.class);
        //this.retencion =  retencion;
        this.factura = factura;
        setEntity(retencion);
       /* binder.setBean(retencion);
        bind();

        getDetalles().setItems(retencion.getDetalles());
        binder.addValueChangeListener(valueChangeEvent -> buttonAceptar.setEnabled(binder.isValid()));*/
    }

    public void setEntity(RetencionImpuesto retencion) {
        // this.binder = new BeanValidationBinder<>(RetencionImpuesto.class);
        this.retencion =  retencion;
        //this.factura = factura;
        binder.setBean(retencion);
        bind();

        getDetalles().setItems(retencion.getDetalles());
        binder.addValueChangeListener(valueChangeEvent -> buttonAceptar.setEnabled(binder.isValid()));
    }





    Window window;

    public void showInModal()
    {
        window = new Window("Retencion de Impuestos");
        window.setModal(true);
        window.setWidth("60%");
        window.center();
        window.setContent(createContent());
        UI.getCurrent().addWindow(window);
    }


    protected Component createContent() {
        ToolBar toolBar = new ToolBar();
        toolBar.addDerecha(buttonAdd);
        toolBar.addDerecha(buttonRemove);

        MHorizontalLayout layourSri = new MHorizontalLayout(claveAcceso,consultarRetencionSri);
        layourSri.expand(claveAcceso);
        layourSri.setWidth("50%");
        layourSri.setSpacing(true);
        layourSri.setCaption("Numero Autorizacion:");

        MFormLayout form = new MFormLayout(layourSri,numero, fecha).withWidth("100%").withHeightUndefined();
        fecha.setWidth("50%");
        return new MVerticalLayout(form, toolBar, getDetalles(), getToolBar())
                .withWidth("100%").withHeightUndefined().withExpand(getDetalles(), 1);
    }


    private MHorizontalLayout getToolBar() {
        return new MHorizontalLayout(buttonAceptar, buttonCancelar).withSpacing(true);
    }


    public MGrid<DetalleRetencionImpuesto> getDetalles() {
        if (detalles == null) {
            ComboBox<CodigoImpuestoRetencion> impuestosCombo = new ComboBox<>();
            impuestosCombo.setWidth("100%");
            impuestosCombo.setItems(codigoImpuestoRetencionRepositorio.findAll());
            MTextField baseImponible = new MTextField().withWidth("100%");
            detalles = new MGrid<>(DetalleRetencionImpuesto.class);
            detalles.removeAllColumns();
            detalles.setWidth("100%");
            detalles.setHeight("200px");
            detalles.getEditor().setEnabled(true);
            detalles.getEditor().setCancelCaption("Cancelar");
            detalles.getEditor().setSaveCaption("Guardar");
            Binder<DetalleRetencionImpuesto> binder = detalles.getEditor().getBinder();
            Grid.Column<DetalleRetencionImpuesto, String> comboColunm = detalles.addColumn(s ->
                    s.getImpuestoRetencion() == null ? "[" + s.getCodigoImpuesto() + "][" + s.getCodigoRetencion() + "] "
                            + s.getNombreImpuesto() : s.getImpuestoRetencion().toString())
                    .setCaption("Impuesto Retencion").setExpandRatio(1);
            Grid.Column<DetalleRetencionImpuesto, BigDecimal> porcentajeImpuestoColumn = detalles.addColumn(detalleRetencionImpuesto -> detalleRetencionImpuesto.getPorcentajeRetencion()
                    .setScale(2, BigDecimal.ROUND_HALF_UP))
                    .setCaption("% Retencion");
            Grid.Column<DetalleRetencionImpuesto, BigDecimal> baseImponibleColumn = detalles.addColumn(detalleRetencionImpuesto -> detalleRetencionImpuesto.getBaseImponble()
                    .setScale(2, BigDecimal.ROUND_HALF_UP))
                    .setCaption("Base Imponible");
            detalles.addColumn(detalleRetencionImpuesto -> detalleRetencionImpuesto.getValorRetenido()).setCaption("Valor Retenido");
            comboColunm.setEditorBinding(binder.forField(impuestosCombo).bind(DetalleRetencionImpuesto::getImpuestoRetencion, DetalleRetencionImpuesto::setImpuestoRetencion));
            baseImponibleColumn.setEditorBinding(binder.forField(baseImponible).withConverter(new StringToBigDecimalConverter("Solo Numero"))
                    .bind(DetalleRetencionImpuesto::getBaseImponble, DetalleRetencionImpuesto::setBaseImponble));
            this.getDetalles().addSelectionListener(selectionEvent ->
                    buttonRemove.setEnabled(!this.getDetalles().asSingleSelect().isEmpty())
            );
        }
        return detalles;
    }


    @PostConstruct
    private void registerListener()
    {
        this.eventBus.subscribe(this);
    }


    @EventBusListenerMethod(scope = EventScope.VIEW)
    public void onRetencionModified(ConsultaRetencionImpuestoEvent event) {
        RetencionImpuesto retencionImpuesto = event.getRetencion();
        setEntity(retencionImpuesto);
    }
}



