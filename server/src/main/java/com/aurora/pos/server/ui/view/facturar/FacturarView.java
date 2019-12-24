package com.aurora.pos.server.ui.view.facturar;

import com.aurora.comprobantes.mappers.FacturaMapper;
import com.aurora.config.ParametrosServer;
import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.SimpleBaseView;
import com.aurora.impuestos.entidades.RetencionImpuesto;
import com.aurora.pos.entidades.*;
import com.aurora.pos.repositorios.EstablecimientoRepositorio;
import com.aurora.pos.repositorios.FacturaRepositorio;
import com.aurora.pos.repositorios.ListaPreciosRepositorio;
import com.aurora.pos.repositorios.PuntoEmisionRepositorio;
import com.aurora.pos.server.reportes.dto.FacturasVentaDto;
import com.aurora.pos.server.reportes.dto.InformacionTributaria;
import com.aurora.pos.servicios.ServiceException;
import com.aurora.pos.servicios.ServicioCorreo;
import com.aurora.pos.servicios.ServicioEmisor;
import com.aurora.pos.servicios.ServicioFacturacion;
import com.aurora.pos.sri.esquemas.XMLSerializador;
import com.aurora.pos.sri.firma.FirmaElectronica;
import com.aurora.reports.MyStreamResource;
import com.aurora.reports.ReportUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import framework.vaadinreports.MostarDocumentosWindows;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.hene.popupbutton.PopupButton;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MFormLayout;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@SpringView(name = FacturarView.VIEW_NAME)
@SideBarItem(sectionId = Sections.TRANSACCIONES,caption = "Facturas",order = 1)
@FontAwesomeIcon(FontAwesome.BARCODE)
public class FacturarView extends SimpleBaseView<Factura> implements View{

    public static final String VIEW_NAME = "facturarView";

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private ListaPreciosRepositorio listaPreciosRepositorio;

    @Autowired
    private FacturaForm facturaForm;


    @Autowired
    private EstablecimientoRepositorio establecimientoRepositorio;

    @Autowired
    private ServicioFacturacion servicioFacturacion;

    @Autowired
    protected EventBus.ViewEventBus eventBus;

    @Autowired
    private FirmaElectronica firmaElectronica;

    @Autowired
    private PuntoEmisionRepositorio puntoEmisionRepositorio;

    @Autowired
    private FacturaMapper facturaMapper;


    @Autowired
    private ServicioEmisor servicioEmisor;


    @Autowired
    private ParametrosServer parametrosServer;

    @Autowired
    private ServicioCorreo servicioCorreo;


    @Autowired
    public RetencionForm retencionForm;



    private Button reporteButton = new Button(FontAwesome.FILE_WORD_O);
    private Button pdfButton = new Button(FontAwesome.FILE_PDF_O);

    public FacturarView() {

        this.getToolBar().addIzquierda(getComboEstablecimietos());
        this.getToolBar().addIzquierda(getComboPuntoEmision());
        this.getToolBar().addIzquierda(getNuevaButton());
        this.getToolBar().addIzquierda(getEditButton());
        this.getToolBar().getContentIzquierda().setComponentAlignment(getNuevaButton(), Alignment.BOTTOM_LEFT);
        this.getToolBar().getContentIzquierda().setComponentAlignment(getEditButton(), Alignment.BOTTOM_LEFT);

        this.getToolBar().addDerecha(reporteButton);
        this.getToolBar().addDerecha(pdfButton);


        pdfButton.addClickListener(clickEvent -> showRIDE());
        this.getToolBar().addDerecha(getFiltroText());
        this.getToolBar().addDerecha(getFechaIncio());
        this.getToolBar().addDerecha(getFechaFiltro());
        this.getToolBar().getContentDerecha().setComponentAlignment(getFiltroText(), Alignment.BOTTOM_LEFT);
        this.getToolBar().getContentDerecha().setComponentAlignment(getFechaFiltro(), Alignment.BOTTOM_LEFT);
    }




    public void showRIDE() {
        Set<Factura> facturaEntities = this.grid.getSelectedItems();
        if (facturaEntities.isEmpty()) return;
        MostarDocumentosWindows pdfWindow = null;
        com.aurora.pos.server.reportes.dto.Factura f = new com.aurora.pos.server.reportes.dto.Factura(facturaEntities.stream().findFirst().get());
        try {
            pdfWindow = new MostarDocumentosWindows(Arrays.asList(f),parametrosServer.getPathReport(), "testReport.jrxml");
            pdfWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Button nuevaButton;
    public Button getNuevaButton() {
        if(this.nuevaButton==null)
        {
            this.nuevaButton = new Button(FontAwesome.PLUS);
            this.nuevaButton.addClickListener(clickEvent ->{


                List<ListaPrecios> listas = this.listaPreciosRepositorio.findAll();
                Factura factura = new Factura();

                PuntoEmision puntoEmision = this.puntoEmisionRepositorio.findOne(getComboPuntoEmision().getValue().getId());
                factura.setPuntoEmision(puntoEmision);


                if(listas.size() == 0)
                {
                    Notification notification = new Notification("Lista Precios",
                            "Para poder facturar debe tener al menos una lista de precios", Notification.Type.TRAY_NOTIFICATION);
                    notification.setDelayMsec(6000);
                    notification.show(Page.getCurrent());
                }
                else if(listas.size() == 1)
                {
                    facturaForm.setEntity(factura,listas.get(0));
                    facturaForm.openInModalPopup();
                }
                else
                {
                    SeleccionarListaPreciosWindows seleccion = new SeleccionarListaPreciosWindows(this.listaPreciosRepositorio);
                    seleccion.openInModalPopPup();
                    seleccion.aceptarButton.addClickListener(clickEvent1 -> {
                        //factura.setListaPrecios();
                        facturaForm.setEntity(factura,seleccion.comboBoxListaPrecios.getValue());
                        facturaForm.openInModalPopup();
                        UI.getCurrent().removeWindow(seleccion);
                    });

                }

            } );
            this.nuevaButton.setDescription("Nueva Factura");
            this.nuevaButton.setEnabled(false);

        }
        return nuevaButton;
    }

    private Button editButton;
    public Button getEditButton()
    {
        if(editButton ==null) {
            editButton = new Button(FontAwesome.PENCIL);
            editButton.setEnabled(false);
            editButton.addClickListener(clickEvent -> {

                Factura factura = getGrid().getSelectedItems().stream().findAny().get();

                List<ListaPrecios> listas = this.listaPreciosRepositorio.findAll();

                if (listas.size() == 0) {
                    Notification notification = new Notification("Lista Precios",
                            "Para poder guardar debe tener al menos una lista de precios", Notification.Type.TRAY_NOTIFICATION);
                    notification.setDelayMsec(6000);
                    notification.show(Page.getCurrent());
                }

                 if (listas.size() == 1) {
                        facturaForm.setEntity(factura, listas.get(0));
                        facturaForm.openInModalPopup();
                 } else {
                        SeleccionarListaPreciosWindows seleccion = new SeleccionarListaPreciosWindows(this.listaPreciosRepositorio);
                        seleccion.openInModalPopPup();
                        seleccion.aceptarButton.addClickListener(clickEvent1 -> {
                            //factura.setListaPrecios();
                            facturaForm.setEntity(factura, seleccion.comboBoxListaPrecios.getValue());
                            facturaForm.openInModalPopup();
                            UI.getCurrent().removeWindow(seleccion);
                        });
                 }

            });
        }

        return editButton;
    }


    private TextField filtroText;
    public TextField getFiltroText() {
        if(filtroText==null)
        {
            filtroText = new MTextField();
            filtroText.setPlaceholder("Buscar...");
            filtroText.addValueChangeListener(valueChangeEvent -> list(valueChangeEvent.getValue()));
        }

        return filtroText;
    }

    private DateField fechaFiltro;
    public DateField getFechaFiltro() {
        if(fechaFiltro==null)
        {
            fechaFiltro = new DateField();
            fechaFiltro.setValue(LocalDate.now());
            fechaFiltro.addValueChangeListener(valueChangeEvent -> list());

        }
        return fechaFiltro;
    }

    private DateField fechaIncio;
    public DateField getFechaIncio() {
        if(fechaIncio==null)
        {
            fechaIncio = new DateField();
            fechaIncio.setValue(LocalDate.now().minusDays(10));
            fechaIncio.addValueChangeListener(valueChangeEvent -> list());

        }
        return fechaIncio;
    }

    @Override
    public MGrid<Factura> getGrid() {
        if(this.grid ==null)
        {
            this.grid = new MGrid<>(Factura.class);
            this.grid.removeAllColumns();
            this.grid.addColumn(factura -> factura.getNumeroDisplay()).setCaption("Numero").setExpandRatio(1);
           // this.grid.addColumn(factura -> this.dateFormat.format(factura.getFechaEmision())).setCaption("Fecha Emision   ");
            this.grid.addColumn(factura -> factura.getCliente().getIdentificacion()).setCaption("Id. Cliente").setExpandRatio(1);
            this.grid.addColumn(factura -> factura.getCliente()!=null?factura.getCliente().getNombre():"")
                    .setCaption("Ciente").setExpandRatio(2);
                     this.grid.addColumn(factura -> factura.getTotalFactura().setScale(6)).setCaption("Total").setExpandRatio(1);

            this.grid.addColumn(factura -> factura.getAmbiente()).setCaption("Ambiente");
            this.grid.addColumn(factura -> factura.getEstado()).setCaption("Estado");
            this.grid.addColumn(factura -> factura.getRetencionImpuesto()!=null?"SI":"NO").setCaption("Retencion");


            this.grid.addComponentColumn(factura -> new ContextMenu(factura)).setCaption("   ").setExpandRatio(1);



            this.grid.addSelectionListener(selectionEvent -> {
               Boolean enable = selectionEvent.getFirstSelectedItem().isPresent()
                       && !selectionEvent.getFirstSelectedItem().get().getEstado().equals("AUTORIZADO");
               getEditButton().setEnabled(enable);




            });

        }

        return this.grid;
    }



    private ComboBox<Establecimiento> comboBoxEstablecimiento;
    public ComboBox<Establecimiento> getComboEstablecimietos()
    {
        if(comboBoxEstablecimiento == null)
        {
            comboBoxEstablecimiento = new ComboBox<>();
            comboBoxEstablecimiento.setTextInputAllowed(false);
            comboBoxEstablecimiento.setItemCaptionGenerator(
                    establecimiento -> establecimiento.getCodigo());
            comboBoxEstablecimiento.setCaption("Establecimiento");

            comboBoxEstablecimiento.addValueChangeListener(valueChangeEvent -> {

                getComboPuntoEmision().setValue(null);
                if(valueChangeEvent.getValue()!=null)
                {
                    getComboPuntoEmision().setEnabled(true);
                    getComboPuntoEmision().setItems(valueChangeEvent.getValue().getPuntos());
                    if(valueChangeEvent.getValue().getPuntos().size()>0)
                        getComboPuntoEmision().setValue(valueChangeEvent.getValue().getPuntos().get(0));

                }
                else
                {
                    getComboPuntoEmision().setEnabled(false);

                }
            });
        }
        return comboBoxEstablecimiento;
    }


    public ComboBox<PuntoEmision> comboPuntoEmision;
    public ComboBox<PuntoEmision> getComboPuntoEmision()
    {
        if(comboPuntoEmision==null) {
            comboPuntoEmision = new ComboBox<>();
            comboPuntoEmision.setEnabled(false);
            comboPuntoEmision.setItemCaptionGenerator(puntoEmision -> puntoEmision.getCodigo());

            comboPuntoEmision.setTextInputAllowed(false);
            comboPuntoEmision.setCaption("Punto de Emision");
            comboPuntoEmision.addValueChangeListener(valueChangeEvent -> {
                getNuevaButton().setEnabled(comboPuntoEmision.getValue() != null);
                list();
            });
        }
        return comboPuntoEmision;
    }





    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        List<Establecimiento> establecimientos = this.establecimientoRepositorio.findAll();
        getComboEstablecimietos().setItems(establecimientos);
        if(establecimientos.size()>0)
            getComboEstablecimietos().setValue(establecimientos.get(0));
        list();
    }





    public void list()
    {
        list("");
    }


    public void list(String filtro)
    {
        if(getComboPuntoEmision().getValue() == null)
        {
            this.grid.setRows(new ArrayList<>());
            return;
        }

        String parametroFilro = "%" + filtro +"%";

        Date fechaInicio = Date.from(this.getFechaIncio().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaInicio.setHours(0);
        fechaInicio.setSeconds(0);

        Date fechaFin = Date.from(this.getFechaFiltro().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaFin.setHours(23);
        fechaFin.setSeconds(59);

        Long idPuntoEmsion = getComboPuntoEmision().getValue().getId();

        List<Factura> facturas = this.facturaRepositorio.filter(parametroFilro,fechaInicio,fechaFin,idPuntoEmsion);

        setReport(facturas.stream().filter(x -> x.getEstado().equals("AUTORIZADO")).collect(Collectors.toList())
                ,fechaInicio,fechaFin);
        this.grid.setRows(facturas);


    }

    private void setReport( List<Factura> facturas,Date fechaInicio,Date fechaFin)
    {
        List<FacturasVentaDto> lista = new ArrayList<>();
        facturas.forEach(x -> lista.add(new FacturasVentaDto(x)));

        Map datosReporte = new HashMap();



        datosReporte.put("fechaInicio",dateFormat.format(fechaInicio));
        datosReporte.put("fechaFin",dateFormat.format(fechaFin));
        datosReporte.put("PATH",parametrosServer.getPathReport());

        if(extension !=null)
            reporteButton.removeExtension(extension);

        String pathReportes  = parametrosServer.getPathReport() + "facturas_emitidas.jrxml";
        BrowserWindowOpener openerPdf =
                new BrowserWindowOpener(new MyStreamResource("facturas.pdf",datosReporte, pathReportes,lista));
        openerPdf.extend(reporteButton);

        extension = reporteButton.getExtensions().stream().findFirst().get();


    }

    Extension extension = null;

    @PostConstruct
    public void init()
    {
        this.eventBus.subscribe(this);
    }


    @EventBusListenerMethod(scope = EventScope.VIEW)
    public void onFacturaModified(FacturaModifiedEvent event) {
       list();
    }





    public class EstadoFacturaForm
    {

        private EstadoEmision estadoEmision;


        public void setEntity(EstadoEmision entity) {
            this.estadoEmision = entity;
        }

        private Window window;



        public Window openInModalPopup() {
            window = new Window("Estado de Emision");
            window.setWidth("80%");
            //window.setHeight("-1px");
            window.center();
            window.setModal(true);

            TextField estadoRecepcion = new MTextField("Estado Recepcion:").withWidth("100%").withEnabled(false);
            TextField estadoAutorizacion = new MTextField("Estado Autorizacion").withWidth("100%").withEnabled(false);
            TextField fechaAutorizacion = new MTextField("Fecha Autorizacion:").withWidth("100%").withEnabled(false);


            MGrid<MensajeProcesoEmision> mensajesRecepcionGrid =
                    new MGrid<>(MensajeProcesoEmision.class)
                            .withProperties("codigo","mensaje","informacionAdcional","identificador")
                            .withColumnHeaders("Codigo","Mensaje","Informacion Adicional","Identificador");
            mensajesRecepcionGrid.setWidth("100%");
            mensajesRecepcionGrid.setHeight("50%");
            //mensajesRecepcionGrid.setEnabled(false);
            mensajesRecepcionGrid.setCaption("Mensajes");

            MGrid<MensajeProcesoEmision> mensajesAutorizacionGrid =
                    new MGrid<>(MensajeProcesoEmision.class)
                            .withProperties("codigo","mensaje","informacionAdcional","identificador")
                            .withColumnHeaders("Codigo","Mensaje","Informacion Adicional","Identificador");
            mensajesAutorizacionGrid.setWidth("100%");
            mensajesAutorizacionGrid.setHeight("50%");
            mensajesAutorizacionGrid.setCaption("Mensajes");
           // mensajesAutorizacionGrid.setEnabled(false);

            TabSheet main = new TabSheet();
            main.setWidth("100%");
            main.setHeightUndefined();


            main.addTab(new MFormLayout(estadoRecepcion,mensajesRecepcionGrid).withWidth("100%")
                    .withExpandRatio(mensajesRecepcionGrid,1f).withMargin(true).withHeight("100%"),"Estado Recepcion");
            main.addTab(new MFormLayout(estadoAutorizacion,fechaAutorizacion,mensajesAutorizacionGrid)
                    .withWidth("100%").withMargin(true).withHeight("100%"),"Estado Autorizacion");

            if(this.estadoEmision!=null)
            {
                estadoRecepcion.setValue(this.estadoEmision.getEstadoRecepcion());
                estadoAutorizacion.setValue(this.estadoEmision.getEstadoAutorizacion()!=null?this.estadoEmision.getEstadoAutorizacion():"");
                fechaAutorizacion.setValue(this.estadoEmision.getFechaAutorizacion()!=null
                        ?this.estadoEmision.getFechaAutorizacion().toString():"");
                mensajesAutorizacionGrid.setRows(this.estadoEmision.getMensajesAutorizacion());
                mensajesRecepcionGrid.setRows(this.estadoEmision.getMensajesRecepcion());
            }
            window.setContent(main);
            UI.getCurrent().addWindow(window);
            return window;
        }


        public void closePopup() {
            if (window!=null)
                UI.getCurrent().removeWindow(window);
        }


        public Window getPopup() {
            return window;
        }
    }




    public class ContextMenu extends PopupButton
    {

        private String NAME_REPORT = "cabeceraFactura.jrxml";

        private VerticalLayout contextLayout = new VerticalLayout();


        Button xmlButton = new MButton(FontAwesome.CODE)
                .withCaption("XML")
                .withStyleName(ValoTheme.BUTTON_LINK);
        Button rideButton = new MButton(FontAwesome.FILE_PDF_O)
                .withCaption("RIDE")
                .withStyleName(ValoTheme.BUTTON_LINK);

        Button estadoButton = new MButton(FontAwesome.EYE)
                .withCaption("Estado")
                .withStyleName(ValoTheme.BUTTON_LINK);

        Button autorizarButton ; //= new MButton(VaadinIcons.FILE_PROCESS).withStyleName(ValoTheme.BUTTON_LINK);
        Button enviarCorreo = new MButton(FontAwesome.PAPER_PLANE)
                .withCaption("Correo")
                .withStyleName(ValoTheme.BUTTON_LINK);

        Button retencionButton = new MButton(FontAwesome.TABLE)
                .withCaption("Rentencion")
                .withStyleName(ValoTheme.BUTTON_LINK);

        Button anularButton = new MButton(FontAwesome.REMOVE)
                .withCaption("Anular")
                .withStyleName(ValoTheme.BUTTON_LINK);

        Button elimnarButton = new MButton(FontAwesome.TRASH)
                .withCaption("Eliminar")
                .withStyleName(ValoTheme.BUTTON_LINK);




        private Factura fact;

        private String fileName;

        public ContextMenu(Factura factura)
        {
            this.setIcon(FontAwesome.ELLIPSIS_H);
            this.setStyleName(ValoTheme.ACCORDION_BORDERLESS);
            this.fileName = "FAC_"+factura.getSerie() + "-" + factura.getNumeroDisplay();

            this.fact = factura;


            autorizarButton = new ConfirmButton("Autorizar",
                    "Esta seguro de emitir la factura?", clickEvent -> {
                try {
                    this.fact = servicioFacturacion.autorizarFactura(this.fact);
                    EstadoEmision estadoDocumento =  this.fact.getEstadoEmision();
                    if(estadoDocumento.getEstadoAutorizacion().equals("AUTORIZADO"))
                    {
                        String correo = factura.getCliente().getCorreo();
                        enviarCorreo(correo, fact);
                    }

                    Notification notification = new Notification("SRI",
                            estadoDocumento!=null? estadoDocumento.getEstado():"NO PROCESADO",
                            Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(5000);
                    notification.setPosition(Position.MIDDLE_CENTER);
                    notification.show(Page.getCurrent());
                    list();

                } catch (Exception e) {
                    e.printStackTrace();
                    Notification notification = new Notification("Error",e.getMessage(), Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(5000);
                    notification.setPosition(Position.MIDDLE_CENTER);
                    notification.show(Page.getCurrent());
                }
            });
            autorizarButton.setIcon(FontAwesome.CHECK_CIRCLE);
            autorizarButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            autorizarButton.setEnabled(!factura.getEstado().equals("AUTORIZADO") &&  !factura.getEstado().equals("ANULADO"));

            this.contextLayout.addComponents(xmlButton,rideButton,estadoButton,autorizarButton,anularButton, elimnarButton,  enviarCorreo,retencionButton);


            BrowserWindowOpener opener = new BrowserWindowOpener(getResourceXml());
            opener.extend(xmlButton);

            BrowserWindowOpener openerPdf = new BrowserWindowOpener(getResourcePdf());
            openerPdf.extend(rideButton);


            anularButton.setEnabled(factura.getEstado().equals("AUTORIZADO") );
            anularButton.addClickListener(clickEvent -> {
                this.setPopupVisible(false);
                ConfirmDialog.show(UI.getCurrent(),"Anular","Desea Anular el Comprobante?","OK","Cancelar",
                        confirmDialog -> {
                            if(confirmDialog.isConfirmed())
                            {
                                factura.setAnulada(true);
                                facturaRepositorio.save(factura);
                                list();
                                Notification.show("Factura Anulada");
                            }
                        });
            });

            elimnarButton.setEnabled(!factura.getEstado().equals("AUTORIZADO") &&  !factura.getEstado().equals("ANULADO"));
            //elimnarButton.setEnabled(factura.getNumeroDisplay().equals("000000000"));
            elimnarButton.addClickListener(clickEvent -> {
                this.setPopupVisible(false);

                ConfirmDialog.show(UI.getCurrent(),"Anular","Desea Eleminar el Comprobante?","OK","Cancelar",
                        confirmDialog -> {
                            if(confirmDialog.isConfirmed())
                            {
                                facturaRepositorio.delete(factura);
                                Notification.show("Factura eliminada");
                                list();
                            }
                        });



            });

            estadoButton.addClickListener(clickEvent -> {
                EstadoFacturaForm estadoFacturaForm = new EstadoFacturaForm();
                estadoFacturaForm.setEntity(factura.getEstadoEmision());
                estadoFacturaForm.openInModalPopup();
                this.setPopupVisible(false);
            });

            enviarCorreo.addClickListener(clickEvent -> {
                Window window = new Window("Enviar Correo");

                TextField correoTxt = new TextField();
                correoTxt.setValue(factura.getCliente().getCorreo());
                correoTxt.setWidth("100%");
                Button enviarButton = new Button(FontAwesome.PAPER_PLANE_O);
                HorizontalLayout verticalLayout = new HorizontalLayout(correoTxt,enviarButton);
                verticalLayout.setComponentAlignment(enviarButton,Alignment.BOTTOM_CENTER);
                verticalLayout.setExpandRatio(correoTxt,1f);
                verticalLayout.setWidth("100%");
                verticalLayout.setHeightUndefined();
                verticalLayout.setMargin(true);

                window.center();
                window.setModal(true);
                window.setWidth("30%");
                enviarButton.addClickListener(clickEvent1 -> {
                    enviarCorreo(correoTxt.getValue(), this.fact);
                    UI.getCurrent().removeWindow(window);
                    Notification.show("Correo Enviado");
                });
                window.setContent(verticalLayout);
                UI.getCurrent().addWindow(window);
                this.setPopupVisible(false);
            });


            retencionButton.addClickListener(clickEvent -> {
                setPopupVisible(false);
                RetencionImpuesto retencionImpuesto;
                if(factura.getRetencionImpuesto() ==null)
                {

                     retencionImpuesto = new RetencionImpuesto();
                    factura.setRetencionImpuesto(retencionImpuesto);
                }
                else
                {
                    retencionImpuesto = factura.getRetencionImpuesto();
                }
                retencionForm.setEntity(factura,retencionImpuesto);
                retencionForm.showInModal();
            });
            this.setContent(this.contextLayout);
            this.addClickListener(clickEvent -> {
               setPopupVisible(true);
            });
        }

        private void enviarCorreo(String correo, Factura f)
        {
            this.fileName = "FAC_"+f.getSerie() + "-" + f.getNumeroDisplay();
            Emisor emisor = servicioEmisor.getEmisor();
            List<ServicioCorreo.Attachment> attachments = new ArrayList<>();

            ServicioCorreo.Attachment attachmentXml = new ServicioCorreo.Attachment();
            attachmentXml.setContentType("application/xml");
            attachmentXml.setFile( new ByteArrayResource(getXmlFile(f)));
            attachmentXml.setFileName(fileName +".xml");

            ServicioCorreo.Attachment attachmentPdf = new ServicioCorreo.Attachment();
            attachmentPdf.setContentType("application/pdf");
            attachmentPdf.setFile( new ByteArrayResource(getPdfFile(f)));
            attachmentPdf.setFileName(fileName +".pdf");

            attachments.add(attachmentXml);
            attachments.add(attachmentPdf);


            try {
                servicioCorreo.sendMessageWithAttachment(correo,
                        emisor.getCorreo().getAsuntoCorreo(),emisor.getCorreo().getCuerpoCorreo(),attachments);
            } catch (ServiceException e) {
                Notification.show("Error envio correo",e.getMessage(), Notification.Type.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }




        public byte[] getXmlFile(Factura f)
        {
            byte[] xmlFile_ = null;
                if (fact.getEstadoEmision() != null &&
                        fact.getEstado().equals("AUTORIZADO") &&
                        fact.getEstadoEmision().getXml() != null) {
                    String xml = fact.getEstadoEmision().getXml();
                    return xml.getBytes(StandardCharsets.UTF_8);
                }

                com.aurora.pos.sri.esquemas.factura_v1.Factura comprobante = facturaMapper.map(f);
                XMLSerializador<com.aurora.pos.sri.esquemas.factura_v1.Factura> xmlSerializador
                        = new XMLSerializador<>(com.aurora.pos.sri.esquemas.factura_v1.Factura.class);
                ByteArrayOutputStream baos = null;
                try {
                    baos = xmlSerializador.serializar(comprobante);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    xmlFile_ = firmaElectronica.firmar(baos);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return xmlFile_;
        }


        public byte[] getPdfFile(Factura f) {
            byte[] pdfFile_ = null;
                Emisor emisor = servicioEmisor.getEmisor();
                if(emisor ==null)
                {
                    Notification.show("No hay emisor definido");
                    return null ;
                }



                Map datosReporte = new HashMap();
                datosReporte.put("P_TITULO", "");
                datosReporte.put("msg", "");
                datosReporte.put("P_SUBTITULO", "");
                datosReporte.put("LOGO_URL",emisor.getPathLogo());
                datosReporte.put("PATH", parametrosServer.getPathReport());
                ReportUtil reportUtil = new ReportUtil(datosReporte, parametrosServer.getPathReport() + NAME_REPORT);
                Collection<com.aurora.pos.server.reportes.dto.Factura> lista = new ArrayList<>();
                com.aurora.pos.server.reportes.dto.Factura facturaDto =
                        new com.aurora.pos.server.reportes.dto.Factura(f);



                InformacionTributaria informacionTributaria = new InformacionTributaria();
                informacionTributaria.setRuc(emisor.getRucEmisor());
                informacionTributaria.setDireccionMatriz(emisor.getDireccionMatriz());
                informacionTributaria.setRazonSocial(emisor.getRazonSocialEmisor());


                facturaDto.setInformacionTributaria(informacionTributaria);
                lista.add(facturaDto);
                try {
                    pdfFile_ = reportUtil.getFile(lista);
                } catch (JRException e) {
                    e.printStackTrace();
                }

            return pdfFile_;
        }


        private Resource getResourceXml()
        {
          return  new StreamResource((StreamResource.StreamSource) () ->
                    new ByteArrayInputStream(getXmlFile(fact)), fileName + ".xml");
        }

        private Resource getResourcePdf()
        {
            return  new StreamResource((StreamResource.StreamSource) () ->
                    new ByteArrayInputStream(getPdfFile(fact)), fileName + ".pdf");
        }
    }

}
