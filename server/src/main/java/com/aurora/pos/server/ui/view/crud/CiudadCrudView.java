package com.aurora.pos.server.ui.view.crud;

import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Ciudad;
import com.aurora.pos.repositorios.CiudadRepositorio;
import com.aurora.config.Sections;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;

/**
 * Created by max on 10/08/17.
 */

//@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Ciudad",order = 2)
//@SpringView(name = CiudadCrudView.VIEW_NAME)
//@VaadinFontIcon(VaadinIcons.OFFICE)
public class CiudadCrudView extends BaseCrudView<Ciudad> {

    public static final String VIEW_NAME = "ciudadCrudView";

    @Autowired
    private CiudadRepositorio ciudadRepositorio;

    @Override
    public Ciudad getNew() {
        return new Ciudad();
    }

    @Override
    public void listEntry(String nameFilter) {
        String likeFilter = "%" + nameFilter + "%";
        List<Ciudad> ciudades = ciudadRepositorio.findByNombreLikeIgnoreCase(likeFilter);
        this.getGrid().setRows(ciudades);
        adjustActionButtonState();

    }

    @Override
    public MGrid<Ciudad> getGrid() {
        if(this.grid == null)
        {
            this.grid =new MGrid<>(Ciudad.class)
                    .withProperties("id", "nombre")
                    .withColumnHeaders("Id", "Nombre");
            this.grid.addColumn(ciudad -> ciudad.getProvincia()!=null?ciudad.getProvincia().getNombre():"*")
                    .setCaption("Provincia");
        }
        return this.grid;
    }


}
