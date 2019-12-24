package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.pos.repositorios.UserRepository;
import com.aurora.seguridad.entidades.Usuario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import javafx.scene.control.CheckBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.v7.fields.MPasswordField;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Usuarios",order = 15)
@FontAwesomeIcon(FontAwesome.USER)
@SpringView(name = UsuarioCrudView.VIEW_NAME)
public class UsuarioCrudView extends BaseCrudView<Usuario> {

    public static final String VIEW_NAME = "usuarioCrudView" ;



    @Autowired
    private UserRepository userRepository;

    @Override
    public Usuario getNew() {
        return new Usuario();
    }

    @Override
    public void listEntry(String nameFilter) {
            String filtro = "%" + nameFilter +"%";
           List<Usuario> usuarios = userRepository.findByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(filtro,filtro);
           this.grid.setItems(usuarios);
        adjustActionButtonState();
    }

    @Override
    public MGrid<Usuario> getGrid() {

        if (this.grid ==null)
        {
            this.grid = new MGrid<>(Usuario.class)
                    .withProperties("email","name","rol")
                    .withColumnHeaders("Correo","Nombre","Rol");
           this.grid.addColumn(usuario -> usuario.isLocked()?"SI":"NO").setCaption("Bloqueado");


        }



        return this.grid;
    }
}
