package com.aurora.inventario.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.inventario.entidades.UnidadMedida;
import com.aurora.inventario.repositorios.UnidadMedidaRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Unidades Medida",order = 6)
@SpringView(name = UnidadMedidaCrudView.VIEW_NAME)
@FontAwesomeIcon(FontAwesome.BALANCE_SCALE)
public class UnidadMedidaCrudView  extends BaseCrudView<UnidadMedida>{

    public static final String VIEW_NAME = "unidadMedidaCrudView";

    @Autowired
    private UnidadMedidaRepositorio unidadMedidaRepositorio;


    @Override
    public UnidadMedida getNew() {
        return new UnidadMedida();
    }

    @Override
    public void listEntry(String nameFilter) {
        String filtro = "%" + nameFilter +"%";
        List<UnidadMedida> unidades = this.unidadMedidaRepositorio.findByNombreLikeOrCodigoLike(filtro,filtro);
        getGrid().setItems(unidades);
        adjustActionButtonState();
    }

    @Override
    public MGrid<UnidadMedida> getGrid() {
        if(this.grid ==null)
        {
            this.grid  = new MGrid<>(UnidadMedida.class)
                            .withProperties("codigo","nombre")
                            .withColumnHeaders("Codigo","Nombre");
        }
        return this.grid;
    }
}
