package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.TarjetaCredito;
import com.aurora.pos.repositorios.TarjetaCreditoRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Tarjetas",order = 10)
@FontAwesomeIcon(FontAwesome.CREDIT_CARD)
@SpringView(name = TarjetaCreditoCrudView.VIEW_NAME)
public class TarjetaCreditoCrudView extends BaseCrudView<TarjetaCredito>{

    public static final String VIEW_NAME = "tarjetaCreditoCrudView";

    @Autowired
    private TarjetaCreditoRepositorio tarjetaCreditoRepositorio;



    @Override
    public TarjetaCredito getNew() {
        return new TarjetaCredito();
    }

    @Override
    public void listEntry(String nameFilter) {
        List<TarjetaCredito> lista = this.tarjetaCreditoRepositorio.findByNombreLike("%"+nameFilter+"%");
        this.grid.setRows(lista);
        adjustActionButtonState();
    }

    @Override
    public MGrid<TarjetaCredito> getGrid() {
        if(grid == null)
        {
            grid = new MGrid<>(TarjetaCredito.class)
                    .withProperties("codigo","nombre")
                    .withColumnHeaders("Codigo","Nombre");
            grid.addColumn(tc -> tc.getBancoEmisor()!=null?tc.getBancoEmisor().getNombre():"").setCaption("Nombre");

        }
        return grid;
    }
}
