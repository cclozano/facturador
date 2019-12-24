package com.aurora.pos.server.ui.view.facturar;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.SimpleBaseView;
import com.aurora.impuestos.entidades.DetalleRetencionImpuesto;
import com.aurora.impuestos.entidades.RetencionImpuesto;
import com.aurora.impuestos.repositorios.RetencionImpuestoRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.DateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@SpringView(name = RentencionesView.VIEW_NAME)
//@SideBarItem(sectionId = Sections.TRANSACCIONES,caption = "Reporte Retenciones",order = 18)
//@VaadinFontIcon(VaadinIcons.ABACUS)
public class RentencionesView  extends SimpleBaseView<DetalleRetencionImpuesto> implements View{

    public static final String VIEW_NAME = "rentencionesView";



    private DateField fechaInicio = new DateField("Fecha Inicio:");
    private DateField fechaFin = new DateField("Fecha Inicio:");


    public RentencionesView()
    {
        fechaInicio.setValue(LocalDate.now().minusDays(5));
        fechaFin.setValue(LocalDate.now());
        fechaInicio.addValueChangeListener(valueChangeEvent -> actualizarGrid());
        fechaInicio.addValueChangeListener(valueChangeEvent -> actualizarGrid());
        getToolBar().addDerecha(fechaInicio);
        getToolBar().addDerecha(fechaFin);
    }


    @Override
    public MGrid<DetalleRetencionImpuesto> getGrid() {
        if(grid ==null)
        {
           grid = new MGrid<>(DetalleRetencionImpuesto.class);
           grid.removeAllColumns();
          // grid.addColumn(imp->imp.getRetencionImpuesto().getFactura().getNumeroCompleto()).setCaption("Numero Factura");
           grid.addColumn(imp->imp.getRetencionImpuesto().getNumero()).setCaption("Numero Retencion");
           grid.addColumn(imp -> dateFormat.format(imp.getRetencionImpuesto().getFecha())).setCaption("Fecha");
           grid.addColumn(imp->imp.getCodigoImpuesto()).setCaption("Cod. Impuesto");
           grid.addColumn(imp->imp.getNombreImpuesto()).setCaption("Nombre Impuesto");
           grid.addColumn(imp->imp.getPorcentajeRetencion()).setCaption("% Retencion");
           grid.addColumn(imp ->imp.getValorRetenido()).setCaption("Valor Retenido");


        }

        return grid;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        actualizarGrid();
    }


    private void actualizarGrid()
    {
        List<DetalleRetencionImpuesto> lista  = new ArrayList<>();
        Date fechaInicio = Date.from(this.fechaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaInicio.setHours(0);
        fechaInicio.setSeconds(0);
        Date fechaFin = Date.from(this.fechaFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        fechaFin.setHours(23);
        fechaFin.setSeconds(59);
        //retencionImpuestoRepositorio.getByFechaBetween(fechaInicio,fechaFin)
        //        .forEach(retencionImpuesto -> lista.addAll(retencionImpuesto.getDetalles()));
        getGrid().setRows(lista);
    }
}
