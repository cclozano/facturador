package com.aurora.inventario.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.inventario.entidades.Servicio;
import com.aurora.inventario.repositorios.ServicioRepositorio;
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

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Servicios",order = 5)
@SpringView(name = ServiciosCrudView.VIEW_NAME)
@FontAwesomeIcon(FontAwesome.FIRE)
public class ServiciosCrudView extends BaseCrudView<Servicio> {
    public static final String VIEW_NAME="serviciosCrudView";

    @Autowired
    private UserService userService;

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Override
    public Servicio getNew() {
        Servicio p = new Servicio();
        Usuario user = userService.getCurrentUser();
        //p.setContribuyente(user.getContribuyente());
        return p;
    }

    @Override
    public void listEntry(String nameFilter) {
        String filtro  = "%" +nameFilter + "%";
        List<Servicio> servicios =
                this.servicioRepositorio.findByNombreLikeOrCodigoLike(filtro,filtro);
        getGrid().setRows(servicios);
        adjustActionButtonState();
    }

    @Override
    public MGrid<Servicio> getGrid() {
        if(this.grid == null)
        {
            this.grid =new MGrid<>(Servicio.class)
                    .withProperties("codigo", "nombre","descripcion")
                    .withColumnHeaders("Codigo", "Nombre","Descripcion");

        }
        return this.grid;
    }
}
