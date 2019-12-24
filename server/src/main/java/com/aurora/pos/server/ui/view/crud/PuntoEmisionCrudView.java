package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.PuntoEmision;
import com.aurora.pos.repositorios.PuntoEmisionRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Punto de Emisión",order = 2)
@FontAwesomeIcon(FontAwesome.MAP)
@SpringView(name = PuntoEmisionCrudView.VIEW_NAME)
public class PuntoEmisionCrudView extends BaseCrudView<PuntoEmision> {

    public static final String VIEW_NAME="puntoEmisionCrudView";

    @Autowired
    private PuntoEmisionRepositorio puntoEmisionRepositorio;

    @Override
    public PuntoEmision getNew() {
        PuntoEmision puntoEmision = new PuntoEmision();
        return puntoEmision;
    }

    @Override
    public void listEntry(String nameFilter) {
        String filter = "%"+nameFilter+"%";
        getGrid().setRows(puntoEmisionRepositorio.findAll());
        adjustActionButtonState();
    }

    @Override
    public MGrid<PuntoEmision> getGrid() {
        if(this.grid==null){
            this.grid=new MGrid<>(PuntoEmision.class)
                    .withProperties("codigo","descripcion","numeroUtimaFactura")
                    .withColumnHeaders("Código","Punto de Emisión","Numero Ultima Factura");
            this.grid.addColumn(puntoEmision -> puntoEmision.getEstablecimiento()!=null?
                    puntoEmision.getEstablecimiento().getCodigo() + " " +
                    puntoEmision.getEstablecimiento().getNombreComercial():"*")
                .setCaption("Establecimiento");
        }
        return this.grid;
    }
}
