package com.aurora.pos.server.ui.view.crud;


import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.entidades.Cliente;
import com.aurora.pos.repositorios.ClienteRepositorio;
import com.aurora.config.Sections;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;

/**
 * Created by max on 23/08/17.
 */
@SpringView(name = ClienteCrudView.VIEW_NAME)
@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Clientes",order = 3)
@FontAwesomeIcon(FontAwesome.USERS)
public class ClienteCrudView extends BaseCrudView<Cliente> implements View{


    public static final String VIEW_NAME = "clienteCrudView";


    @Autowired
    private ClienteRepositorio clienteRepositorio;




    @Override
    public MGrid<Cliente> getGrid() {
        if(this.grid == null) {
            return new MGrid<>(Cliente.class)
                    .withProperties("id","nombre","identificacion","correo")
                    .withColumnHeaders("Id","Nombre","Identificacion","Correo");

        }
        return this.grid;
    }

    @Override
    public Cliente getNew() {
        return new Cliente();
    }

    @Override
    public void listEntry(String nameFilter) {
        String likeFilter = "%" + nameFilter + "%";
        List<Cliente> clientes = clienteRepositorio.filer(likeFilter);
        grid.setRows(clientes);
        adjustActionButtonState();
    }



  
}
