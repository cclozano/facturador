package com.aurora.pos.server.ui.view.crud;

import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Establecimiento;
import com.aurora.config.Sections;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;



@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Establecimientos",order = 1)
@FontAwesomeIcon(FontAwesome.BARS)
@SpringView(name = EstablecimientoCrudView.VIEW_NAME)
public class EstablecimientoCrudView extends BaseCrudView<Establecimiento> {

    public static final String VIEW_NAME="establecimientoCrudView";

    @Override
    public Establecimiento getNew() {
       return new Establecimiento();
    }

    @Override
    public void listEntry(String nameFilter) {

        this.getGrid().setRows(this.repository.findAll());
        adjustActionButtonState();
    }

    @Override
    public MGrid<Establecimiento> getGrid() {
        if(this.grid == null)
        {
            this.grid =new MGrid<>(Establecimiento.class)
                    .withProperties("codigo", "nombreComercial","nombreCorto")
                    .withColumnHeaders("Codigo", "Nombre Comercial","Nombre Corto");
        }
        return this.grid;
    }
}
