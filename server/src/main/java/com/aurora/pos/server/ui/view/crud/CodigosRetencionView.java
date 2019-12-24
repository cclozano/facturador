package com.aurora.pos.server.ui.view.crud;

import com.aurora.config.Sections;
import com.aurora.framework.vaadinext.crud.BaseCrudView;
import com.aurora.impuestos.entidades.CodigoImpuestoRetencion;
import com.aurora.impuestos.repositorios.CodigoImpuestoRetencionRepositorio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;


@SideBarItem(sectionId = Sections.CATALOGOS,caption = "Codigos Retencion",order = 11)
@SpringView(name = CodigosRetencionView.VIEW_NAME)
@FontAwesomeIcon(FontAwesome.CODE)
public class CodigosRetencionView extends BaseCrudView<CodigoImpuestoRetencion>{

    public static final String VIEW_NAME = "codigosRetencionView";


    @Autowired
    private CodigoImpuestoRetencionRepositorio codigoImpuestoRetencionRepositorio;


    @Override
    public CodigoImpuestoRetencion getNew() {
        return new CodigoImpuestoRetencion();
    }

    @Override
    public void listEntry(String nameFilter) {
        String filtro = "%"+nameFilter+"%";
        List<CodigoImpuestoRetencion> lista =  codigoImpuestoRetencionRepositorio.findByCodigoImpuestoLikeOrCodigoRetencionLikeOrDescripcionLike(filtro,filtro,filtro);
        getGrid().setRows(lista);
        adjustActionButtonState();
    }

    @Override
    public MGrid<CodigoImpuestoRetencion> getGrid() {
        if(this.grid ==null)
        {
            this.grid  = new MGrid<>(CodigoImpuestoRetencion.class)
                    .withProperties("codigoImpuesto","codigoRetencion","descripcion","porcentaje")
                    .withColumnHeaders("Codigo Impuesto","Codigo Retencion","Descripcion","Porcentaje Retencion");
            this.grid.getColumn("descripcion").setExpandRatio(1);
        }
        return this.grid;
    }
}
