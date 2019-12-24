package com.aurora.pos.server.ui.view.crud;

import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Provincia;
import com.aurora.pos.repositorios.ProvinciaRepositorio;
import com.aurora.config.Sections;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;

/**
 * Created by max on 10/08/17.
 */

//@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Provincias",order = 1)
//@VaadinFontIcon(VaadinIcons.MAP_MARKER)
//@SpringView(name = ProvinciaCrudView.VIEW_NAME)
public class ProvinciaCrudView extends BaseCrudView<Provincia> implements View{


    public static final String VIEW_NAME = "provinciaCrudView";


    @Autowired
    private ProvinciaRepositorio provinciaRepositorio;


    @Override
    public Provincia getNew() {
        return new Provincia();
    }

    @Override
    public void listEntry(String nameFilter) {
        String likeFilter = "%" + nameFilter + "%";
        List<Provincia> provincias = provinciaRepositorio.findByNombreLike(likeFilter);
        grid.setRows(provincias);
        adjustActionButtonState();
    }

    @Override
    public MGrid<Provincia> getGrid() {
        if(this.grid == null)
        {
            this.grid =new MGrid<>(Provincia.class)
                    .withProperties("id", "nombre")
                    .withColumnHeaders("Id", "Nombre");
        }
        return this.grid;
    }





}
