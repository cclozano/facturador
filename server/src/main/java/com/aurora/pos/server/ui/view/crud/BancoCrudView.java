package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Banco;
import com.aurora.pos.repositorios.BancoRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Banco",order = 11)
@SpringView(name = BancoCrudView.VIEW_NAME)
@FontAwesomeIcon(FontAwesome.BANK)
public class BancoCrudView extends BaseCrudView<Banco>{

    public static final String VIEW_NAME = "bancoCrudView";


    @Autowired
    private BancoRepositorio repositorio;

    @Override
    public Banco getNew() {
        return new Banco();
    }

    @Override
    public void listEntry(String nameFilter) {
            String filtro = "%"+nameFilter+"%";
            this.grid.setRows(repositorio.findByNombreLike(filtro));
        adjustActionButtonState();
    }

    @Override
    public MGrid<Banco> getGrid() {

        if(grid ==null)
        {
            grid = new MGrid<>(Banco.class)
                    .withProperties("codigo","nombre")
                    .withColumnHeaders("Codigo","Nombre");
        }

        return grid;
    }
}
