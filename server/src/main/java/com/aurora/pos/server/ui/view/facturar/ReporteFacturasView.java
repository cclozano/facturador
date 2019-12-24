package com.aurora.pos.server.ui.view.facturar;

import com.aurora.config.ParametrosServer;
import com.aurora.framework.vaadinext.SimpleBaseView;
import com.aurora.reports.ReportUtil;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.repositorios.FacturaRepositorio;
import com.aurora.pos.server.reportes.dto.FacturasVentaDto;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Extension;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.grid.MGrid;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


//@SpringView(name = ReporteFacturasView.VIEW_NAME)
//@SideBarItem(sectionId = Sections.TRANSACCIONES,caption = "Reporte Ventas",order = 1)
//@VaadinFontIcon(VaadinIcons.CHART_GRID)
public class ReporteFacturasView extends SimpleBaseView<FacturasVentaDto> implements View{

    public static final String VIEW_NAME= "reporteFacturasView";

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    private DateField fechaInicioField = new DateField("Fecha Inicio");
    private DateField fechaFinField = new DateField("Fecha Fin");

    @Autowired
    private ParametrosServer parametrosServer;


    public ReporteFacturasView() {
        getToolBar().addDerecha(getGenerarReporte());
        getToolBar().getContentDerecha().setComponentAlignment(getGenerarReporte(), Alignment.BOTTOM_RIGHT);
        getToolBar().addDerecha(fechaInicioField);
        getToolBar().addDerecha(fechaFinField);

        this.fechaInicioField.setValue(LocalDate.now().minusDays(5));
        this.fechaFinField.setValue(LocalDate.now());
        this.fechaFinField.addValueChangeListener(valueChangeEvent -> list());
        this.fechaInicioField.addValueChangeListener(valueChangeEvent -> list());
    }


    private Button generarReporte;

    public Button getGenerarReporte() {
        if(generarReporte ==null)
        {
            generarReporte = new Button(VaadinIcons.FILE_TABLE);

        }
        return generarReporte;
    }

    @Override
    public MGrid<FacturasVentaDto> getGrid() {

        if(grid ==null) {
            grid = new MGrid<>(FacturasVentaDto.class)
                    .withProperties("numeroFactura","fecha","nombreCliente","rucCLiente","subtotal12","subtotal0","iva12","total")
            .withColumnHeaders("Numero","Fecha","Cliente","Ruc","Subtotal 12","Subtotal 0","Iva 12","Total");

        }
        return this.grid;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        list();
    }



    private void list() {


        LocalDate localDate = this.fechaInicioField.getValue();
        Date fechaInicio = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaInicio.setHours(0);
        fechaInicio.setSeconds(0);

        Date fechaFin = Date.from(fechaFinField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaFin.setHours(23);
        fechaFin.setSeconds(59);



        List<Factura> facturas = this.facturaRepositorio.filter(fechaInicio,fechaFin);
        List<FacturasVentaDto> lista = new ArrayList<>();
        facturas.forEach(x -> lista.add(new FacturasVentaDto(x)));


        Map datosReporte = new HashMap();

        BigDecimal total12 = lista.stream().map(FacturasVentaDto::getSubtotal12).reduce(BigDecimal::add).get();
        datosReporte.put("total12",total12.setScale(2, RoundingMode.HALF_UP));

        BigDecimal total0 = lista.stream().map(FacturasVentaDto::getSubtotal0).reduce(BigDecimal::add).get();
        datosReporte.put("total0",total0.setScale(2,RoundingMode.HALF_UP));

        BigDecimal total = lista.stream().map(FacturasVentaDto::getTotal).reduce(BigDecimal::add).get();
        datosReporte.put("total",total.setScale(2,RoundingMode.HALF_UP));

        datosReporte.put("fechaInicio",dateFormat.format(fechaInicio));
        datosReporte.put("fechaFin",dateFormat.format(fechaFin));




        ReportUtil reportUtil = new ReportUtil(datosReporte, parametrosServer.getPathReport() + "facturas_emitidas.jrxml");

        byte [] pdf = new byte[0];
        try {
            pdf = reportUtil.getFile(lista);
        } catch (JRException e) {
            e.printStackTrace();
        }

        if(extension !=null)
            getGenerarReporte().removeExtension(extension);

        BrowserWindowOpener openerPdf = new BrowserWindowOpener(getResourcePdf(pdf));
        openerPdf.extend(getGenerarReporte());

        extension = getGenerarReporte().getExtensions().stream().findFirst().get();


        this.grid.setRows(lista);
    }

    Extension extension = null;


    private Resource getResourcePdf(byte[] pdf)
    {
        return  new StreamResource((StreamResource.StreamSource) () ->
                new ByteArrayInputStream(pdf),"reporte_ventas.pdf");
    }
}
