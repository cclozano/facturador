package com.aurora.inventario.ui.view.crud;

import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.inventario.entidades.Producto;
import com.aurora.config.Sections;
import com.aurora.pos.server.seguridad.UserService;
import com.aurora.seguridad.entidades.Usuario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Productos",order = 4)
@SpringView(name = ProductoCrudView.VIEW_NAME)
@FontAwesomeIcon(FontAwesome.PRODUCT_HUNT)
public class ProductoCrudView extends BaseCrudView<Producto>{

    public static final String VIEW_NAME="productoCrudView";

    @Override
    public Producto getNew() {
        Producto p = new Producto();
        return p;
    }

    @Override
    public void listEntry(String nameFilter) {
      getGrid().setRows(this.repository.findAll());
      adjustActionButtonState();
    }

    @Override
    public MGrid<Producto> getGrid() {
        if(this.grid == null)
        {
            this.grid =new MGrid<>(Producto.class)
                    .withProperties("codigo", "nombre","descripcion")
                    .withColumnHeaders("Codigo", "Nombre","Descripcion");

        }
        return this.grid;
    }
}
