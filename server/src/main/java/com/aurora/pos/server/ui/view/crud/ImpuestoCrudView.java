package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Impuesto;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.repositorios.ImpuestoRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Impuestos",order = 9)
@FontAwesomeIcon(FontAwesome.DOLLAR)
@SpringView(name = ImpuestoCrudView.VIEW_NAME)
public class ImpuestoCrudView extends BaseCrudView<Impuesto> {

    public static final String VIEW_NAME = "impuestoCrudView";

    @Autowired
    private ImpuestoRepositorio impuestoRepositorio;

    @Override
    public Impuesto getNew() {
        return new Impuesto();
    }

    @Override
    public void listEntry(String nameFilter) {
        List<Impuesto> lista = this.impuestoRepositorio.findAll();
        getGrid().setRows(lista);
        adjustActionButtonState();
    }

    @Override
    public MGrid<Impuesto> getGrid() {
        if(grid==null)
        {
            grid = new MGrid<>(Impuesto.class)
                    .withProperties("codigo","codigoPorcentaje","tarifa","nombre","descripcion")
                    .withColumnHeaders("Codigo","Codigo Porcentaje","Tarifa","Nombre","Descipcion");

            grid.getColumn("nombre").setExpandRatio(1);
            grid.getColumn("descripcion").setExpandRatio(1);
        }
        return grid;
    }
}
