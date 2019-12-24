package com.aurora.pos.server.ui.view.facturar;

import com.aurora.framework.vaadinext.ToolBar;
import com.aurora.inventario.entidades.Producto;
import com.aurora.inventario.ui.view.crud.forms.ProductoFom;
import com.aurora.pos.entidades.Cliente;
import com.aurora.pos.entidades.DetalleFactura;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.repositorios.ClienteRepositorio;
import com.aurora.pos.repositorios.ListaPreciosRepositorio;
import com.aurora.pos.server.ui.view.crud.forms.ClientModifiedEvent;
import com.aurora.pos.server.ui.view.crud.forms.ClienteForm;
import com.aurora.pos.servicios.ServicioFacturacion;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.layouts.MVerticalLayout;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;



@SpringComponent
@PrototypeScope
public class FacturaForm  {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteForm clienteForm;

    @Autowired
    private ProductoFom productoFom;

    @Autowired
    protected EventBus.ViewEventBus eventBus;

    @Autowired
    private ServicioFacturacion servicioFacturacion;


    @Autowired
    private ListaPreciosRepositorio listaPreciosRepositorio;

    private Window window ;

    private Factura factura;

    private DetalleFacturaGrid detalleFacturaGrid = new DetalleFacturaGrid(new ListaPrecios());


    public FacturaForm()
    {

    }

    private DateField fechaEmision ;

    private TextField numeroTextField=new TextField("Numero");

    public DateField getFechaEmision() {
        if(fechaEmision ==null)
        {
            fechaEmision = new DateField();
            fechaEmision.setWidth("400px");
            fechaEmision.setValue(LocalDate.now());
            fechaEmision.setDateFormat("dd-MM-yyyy");
            fechaEmision.setCaption("Fecha Emision");
        }
        return fechaEmision;
    }

    public MVerticalLayout getMainLayaut()
    {
       ToolBar barSuperior = new ToolBar();
       barSuperior.addIzquierda(getFechaEmision());
       barSuperior.addIzquierda(numeroTextField);
       barSuperior.addDerecha(getGuardarButton());
       barSuperior.setComponentAlignment(barSuperior.getContentDerecha(),Alignment.BOTTOM_RIGHT);

       Button agregarDetalle = new Button("Agregar");
       agregarDetalle.setIcon(FontAwesome.PLUS_CIRCLE);
       agregarDetalle.setStyleName(ValoTheme.BUTTON_LINK);
       agregarDetalle.addClickListener(clickEvent -> {
           this.factura.agregarDetalle(new DetalleFactura());
           detalleFacturaGrid.setFactura(factura);
       });
       ToolBar bar = new ToolBar();
       bar.addIzquierda(getComboClientes());
       bar.addIzquierda(getVerNuevoCliente());
       bar.addIzquierda(getVerClienteButton());
       bar.addIzquierda(getFormaPagoButton());

       bar.getContentIzquierda().setComponentAlignment(getVerNuevoCliente(),Alignment.BOTTOM_LEFT);
       bar.getContentIzquierda().setComponentAlignment(getVerClienteButton(),Alignment.BOTTOM_LEFT);
       bar.getContentIzquierda().setComponentAlignment(getFormaPagoButton(),Alignment.BOTTOM_LEFT);



        bar.addDerecha(agregarDetalle);
        bar.addDerecha(getVerNuevoProducto());
       MVerticalLayout mainLayout = new MVerticalLayout(
                barSuperior,
                bar,
                detalleFacturaGrid).expand(detalleFacturaGrid)
                .withWidth("100%").withHeight("100%").withMargin(true)
                .withSpacing(true);
        return mainLayout;

    }

    private Button formaPagoButton;

    public Button getFormaPagoButton() {
        if(formaPagoButton ==null)
        {
            formaPagoButton = new Button(FontAwesome.DOLLAR);
            formaPagoButton.setDescription("Formas FormaPago");
        }
        return formaPagoButton;
    }

    private Button guardarButton;
    public Button getGuardarButton() {
        if(this.guardarButton ==null)
        {
            this.guardarButton = new Button(FontAwesome.DOWNLOAD);
            this.guardarButton.setDescription("Guardar");
            this.guardarButton.addClickListener(clickEvent ->{
                if(factura.getFormaPagos()==null||factura.getFormaPagos().size()==0)
                    factura.generarFormaPago();

                LocalDate localDate = fechaEmision.getValue();
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                factura.setFechaEmision(date);

                try {
                    servicioFacturacion.guardar(factura);
                    UI.getCurrent().removeWindow(window);
                    eventBus.publish(this,new FacturaModifiedEvent(factura));
                }
                catch (ConstraintViolationException ex)
                {
                   StringBuilder sb = new StringBuilder();
                   for(ConstraintViolation item : ex.getConstraintViolations())
                   {
                       sb.append(item.getPropertyPath());
                       sb.append(" ");
                       sb.append(item.getMessage());

                   }
                   Notification.show("Error","Error al guardar: " + sb.toString(), Notification.Type.TRAY_NOTIFICATION);
                }
                catch (Exception ex)
                {
                    Notification.show("Error","Error al guardar: " + ex.getMessage(), Notification.Type.TRAY_NOTIFICATION);
                    ex.printStackTrace();
                }

            } );

        }
        return guardarButton;
    }

    private Button verClienteButton;
    private Button getVerClienteButton()
    {
        if (verClienteButton ==null)
        {
            verClienteButton = new Button(FontAwesome.EYE);
            verClienteButton.addClickListener(clickEvent -> {
                clienteForm.setEntity(getComboClientes().getValue());
                clienteForm.openInModalPopup();
            });
            verClienteButton.setEnabled(false);
            verClienteButton.setDescription("Consultar o modificar cliente");
        }
        return verClienteButton;
    }


    private ComboBox<Cliente> comboClientes;
    public ComboBox<Cliente> getComboClientes()
    {
        if(comboClientes == null)
        {
            comboClientes = new ComboBox<>();
            comboClientes.setWidth("400px");
            comboClientes.setCaption("Clientes");
            comboClientes.setItemCaptionGenerator( cliente ->
                    cliente.getIdentificacion() +  " " + cliente.getNombre());
            comboClientes.addValueChangeListener(mValueChangeEvent ->{
                getVerClienteButton().setEnabled(mValueChangeEvent.getValue()!=null);
                factura.setCliente(comboClientes.getValue());
            } );
            comboClientes.setPlaceholder("Seleccione un clente...");
            comboClientes.setItems(clienteRepositorio.findAll());


        }
        return comboClientes;
    }

    private Button verNuevoCliente;
    public Button getVerNuevoCliente() {
        if(verNuevoCliente ==null)
        {
            verNuevoCliente = new Button(FontAwesome.PLUS_CIRCLE);
            verNuevoCliente.addClickListener(clickEvent -> {
                clienteForm.setEntity(new Cliente());
                clienteForm.openInModalPopup();
            });
            verNuevoCliente.setDescription("Agregar CLiente");
        }
        return verNuevoCliente;
    }

    private Button verNuevoProducto;
    public Button getVerNuevoProducto() {
        if(verNuevoProducto ==null)
        {
            verNuevoProducto = new Button(FontAwesome.PLUS_SQUARE_O);
            verNuevoProducto.addClickListener(clickEvent -> {
                productoFom.setEntity(new Producto());
                productoFom.openInModalPopup();
                productoFom.addEditEndListener(item -> {
                    SeleccionarListaPreciosWindows seleccion = new SeleccionarListaPreciosWindows(this.listaPreciosRepositorio);
                    seleccion.openInModalPopPup();
                    seleccion.aceptarButton.addClickListener(clickEvent1 -> {
                        ListaPrecios l = seleccion.comboBoxListaPrecios.getValue();
                        listaPreciosRepositorio.save(l);
                        UI.getCurrent().removeWindow(seleccion);
                        productoFom.closePopup();
                        DetalleFactura df = new DetalleFactura();
                        df.setItem(item);
                        this.factura.agregarDetalle(df);
                        detalleFacturaGrid.setFactura(factura);
                    });
                });
            });
            verNuevoProducto.setDescription("Agregar Producto");
        }
        return verNuevoProducto;
    }

    public void setEntity(Factura factura, ListaPrecios listaPrecios)
    {
        this.factura = factura;
        this.getFechaEmision().setValue(factura.getFechaEmision().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.getComboClientes().setValue(factura.getCliente());
        this.numeroTextField.setValue(factura.getNumeroDisplay());
        this.numeroTextField.setEnabled(false);
        this.detalleFacturaGrid.setListaPrecios(listaPrecios);
        this.detalleFacturaGrid.setFactura(factura);
    }

    public void openInModalPopup()
    {
        window =  new Window();
        window.setCaption("Factura");
        window.center();
        window.setWidth("90%" );
        window.setHeight("90%");
        window.setModal(true);
        window.setContent(getMainLayaut());
        UI.getCurrent().addWindow(window);
    }

    @EventBusListenerMethod(scope = EventScope.VIEW)
    public void onCLientModified(ClientModifiedEvent event) {
        this.comboClientes.setItems(this.clienteRepositorio.findAll());
        this.comboClientes.setValue(event.getEntity());
        clienteForm.closePopup();
    }

    @PostConstruct
    private void init()
    {
        this.eventBus.subscribe(this);
    }




}
