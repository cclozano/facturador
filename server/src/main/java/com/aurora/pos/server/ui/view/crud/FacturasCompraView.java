package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.ParametrosServer;
import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.reports.MyStreamResource;
import com.aurora.impuestos.entidades.FacturaCompra;
import com.aurora.impuestos.repositorios.FacturaCompraRepositorio;
import com.aurora.pos.server.ui.view.ConsultaFacturaModifiedEvent;
import com.aurora.pos.server.ui.view.ConsultaFacturasCompra;
import com.aurora.pos.server.ui.view.crud.forms.FacturaCompraForm;
import com.aurora.pos.sri.esquemas.factura_v1.Factura;

import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.grid.MGrid;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@SpringView(name = FacturasCompraView.VIEW_NAME)
@SideBarItem(sectionId = Sections.TRANSACCIONES,caption = "Facturas Compras",order = 4)
@FontAwesomeIcon(FontAwesome.FILE_WORD_O)
public class FacturasCompraView extends BaseCrudView<FacturaCompra> {

    public static final String VIEW_NAME = "facturasCompraView";


    @Autowired
    private FacturaCompraRepositorio facturaCompraRepositorio;

    @Autowired
    private ConsultaFacturasCompra consultaFacturasCompra;


    @Autowired
    private ParametrosServer parametrosServer;


    private DateField fechaIncioField = new DateField();
    private DateField fechaFinField = new DateField();


    public FacturasCompraView()
    {
        fechaFinField.setValue(LocalDate.now());
        LocalDate localDate = LocalDate.now();
        localDate.minusDays(5);
        fechaIncioField.setValue(localDate);
        getToolBar().addDerecha(getReportButton());
        getToolBar().addDerecha(fechaIncioField);
        getToolBar().addDerecha(fechaFinField);

        fechaIncioField.addValueChangeListener(valueChangeEvent -> {

            String filtro = this.filterByName.getValue();
            listEntry(filtro);

        });

        fechaFinField.addValueChangeListener(valueChangeEvent -> {

            String filtro = this.filterByName.getValue();
            listEntry(filtro);

        });

        getToolBar().addIzquierda(getCosultarFacturaSri());
    }

    private Button cosultarFacturaSri;

    public Button getCosultarFacturaSri() {
        if(cosultarFacturaSri ==null)
        {
            cosultarFacturaSri = new Button(FontAwesome.PLUS_CIRCLE);
            cosultarFacturaSri.addClickListener(clickEvent ->
               consultaFacturasCompra.show()
            );
        }
        return cosultarFacturaSri;
    }

    private Button reportButton;

    public Button getReportButton() {
        if(reportButton == null)
        {
            reportButton = new Button(FontAwesome.TABLE);
        }
        return reportButton;
    }

    @Override
    public FacturaCompra getNew() {
        return new FacturaCompra();
    }

    @Override
    public void listEntry(String filtro) {

        String parametroFilro = "%" + filtro +"%";


        Date fechaInicio = Date.from(this.fechaIncioField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaInicio.setHours(0);
        fechaInicio.setSeconds(0);

        Date fechaFin = Date.from(this.fechaFinField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaFin.setHours(23);
        fechaFin.setSeconds(59);


        List<FacturaCompra> facturaCompras = facturaCompraRepositorio.filter(parametroFilro,fechaInicio,fechaFin);
        if(facturaCompras == null) facturaCompras = new ArrayList<>();

        Map datosReporte = new HashMap();
        datosReporte.put("fechaInicio",dateFormat.format(fechaInicio));
        datosReporte.put("fechaFin",dateFormat.format(fechaFin));

        if(extension !=null)
            getReportButton().removeExtension(extension);

        String pathReport = parametrosServer.getPathReport() + "facturas_compras.jrxml";
        BrowserWindowOpener openerPdf
                = new BrowserWindowOpener(new MyStreamResource("facturas.pdf",datosReporte,pathReport,facturaCompras));
        openerPdf.extend(getReportButton());
        extension = getReportButton().getExtensions().stream().findFirst().get();


        getGrid().setRows(facturaCompras);
        adjustActionButtonState();

    }

    Extension extension = null;


    private Resource getResourcePdf(byte[] pdf)
    {
        return  new StreamResource((StreamResource.StreamSource) () ->
                new ByteArrayInputStream(pdf),"facturas_compras.pdf");
    }

    @Override
    public MGrid<FacturaCompra> getGrid() {

        if(this.grid ==null)
        {
            this.grid = new MGrid<>(FacturaCompra.class);
            this.grid.removeAllColumns();
            this.grid.addColumn(facturaCompra -> facturaCompra.getNumero()).setCaption("Numero");
            this.grid.addColumn(facturaCompra -> dateFormat.format(facturaCompra.getFecha())).setCaption("Fecha");
            this.grid.addColumn(facturaCompra -> facturaCompra.getRuc()).setCaption("Ruc");
            this.grid.addColumn(facturaCompra -> facturaCompra.getNombre()).setCaption("Nombre");
            this.grid.addColumn(facturaCompra -> facturaCompra.getTotalFactura()).setCaption("Total");
        }
        return this.grid;
    }


    @EventBusListenerMethod(scope = EventScope.VIEW)
    public void onFacturaModified(ConsultaFacturaModifiedEvent event) {
        Factura factura = event.getFactura();
        FacturaCompra facturaCompra = new FacturaCompra();
        facturaCompra.setRuc(factura.getInfoTributaria().getRuc());
        facturaCompra.setNombre(factura.getInfoTributaria().getNombreComercial());
        facturaCompra.setNumero(factura.getInfoTributaria().getEstab() + factura.getInfoTributaria().getPtoEmi()
                + "-" +factura.getInfoTributaria().getSecuencial());
        facturaCompra.setClaveAcceso(factura.getInfoTributaria().getClaveAcceso());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        try {
             fecha = simpleDateFormat.parse(factura.getInfoFactura().getFechaEmision());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BigDecimal total0 = BigDecimal.ZERO;
        BigDecimal total12 = BigDecimal.ZERO;

        for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto item : factura.getInfoFactura().getTotalConImpuestos().getTotalImpuesto())
        {
            if(item.getCodigo().equals("2") && item.getCodigoPorcentaje().equals("0"))
            {
                total0 = total0.add(item.getBaseImponible());
            }
            else if(item.getCodigo().equals("2") && item.getCodigoPorcentaje().equals("2"))
            {
                total12 = total12.add(item.getBaseImponible());
            }

        }

        facturaCompra.setSubtotal0(total0);
        facturaCompra.setSubtotal12(total12);
        facturaCompra.setFecha(fecha);
        this.form.setEntity(facturaCompra);
        ((FacturaCompraForm)this.form).enableAceptar();
        this.form.openInModalPopup();
    }
}
