package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.impuestos.entidades.TipoFacturaCompra;
import com.aurora.impuestos.repositorios.TipoFacturaCompraRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Tipos Gastos",order = 16)
@FontAwesomeIcon(FontAwesome.BUYSELLADS)
@SpringView(name = TipoFacturaCompraView.VIEW_NAME)
public class TipoFacturaCompraView extends BaseCrudView<TipoFacturaCompra> {

    public static final String VIEW_NAME="tipoFacturaCompraView";


    @Override
    public TipoFacturaCompra getNew() {
        return new TipoFacturaCompra();
    }


    @Autowired
    private TipoFacturaCompraRepositorio tipoFacturaCompraRepositorio;

    @Override
    public void listEntry(String nameFilter) {
        String filtro = "%"+nameFilter+"%";
        List<TipoFacturaCompra> lista  = this.tipoFacturaCompraRepositorio.findAllByNombreLike(filtro);
        this.getGrid().setRows(lista);
        adjustActionButtonState();
    }

    @Override
    public MGrid<TipoFacturaCompra> getGrid() {
        if(grid == null)
        {
            grid = new MGrid<>(TipoFacturaCompra.class)
                    .withProperties("nombre","detalle","tipoGasto")
                    .withColumnHeaders("Nombre","detalle","Tipo Gasto");
        }
        return this.grid;
    }
}
