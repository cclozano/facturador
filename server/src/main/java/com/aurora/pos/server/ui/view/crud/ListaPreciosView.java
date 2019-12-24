package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.repositorios.ItemRepositorio;
import com.aurora.pos.entidades.DetalleListaPrecios;
import com.aurora.pos.entidades.ListaPrecios;
import com.aurora.pos.repositorios.ListaPreciosRepositorio;
import com.aurora.pos.server.seguridad.UserService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Lista Precios",order = 7)
@FontAwesomeIcon(FontAwesome.LIST)
@SpringView(name = ListaPreciosView.VIEW_NAME)
public class ListaPreciosView extends BaseCrudView<ListaPrecios>{

    public static final String VIEW_NAME="listaPreciosView";

    @Autowired
    private UserService userService;

    @Autowired
    private ListaPreciosRepositorio listaPreciosRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Override
    public ListaPrecios getNew() {
        ListaPrecios listaPrecios = new ListaPrecios();

        List<Item> items = this.itemRepositorio.findAll();
        for (Item x : items)
        {
            DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
            detalleListaPrecios.setItem(x);
            listaPrecios.agregarDetalle(detalleListaPrecios);

        }
        return listaPrecios;
    }

    @Override
    public void listEntry(String nameFilter) {
        String filter = "%"+nameFilter+"%";
        getGrid().setRows(listaPreciosRepositorio.filter(filter));
        adjustActionButtonState();
    }

    @Override
    public MGrid<ListaPrecios> getGrid() {
        if(this.grid ==null) {
            this.grid = new MGrid<>(ListaPrecios.class)
                    .withProperties("id", "nombreCorto")
            .withColumnHeaders("Id","Nombre Corto");
            this.grid.addColumn(listaPrecios -> simpleDateFormat.format(listaPrecios.getFechaElaboracion())).setCaption("Fecha Creacion");
            this.grid.addColumn(listaPrecios -> listaPrecios.isActivo()?"SI":"NO").setCaption("Activa");
        }
        return this.grid;

    }
}
