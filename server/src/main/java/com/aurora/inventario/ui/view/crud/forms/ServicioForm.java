package com.aurora.inventario.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.inventario.entidades.Producto;
import com.aurora.inventario.entidades.Servicio;
import com.aurora.pos.entidades.Impuesto;
import com.aurora.pos.repositorios.ImpuestoRepositorio;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TwinColSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@ViewScope
@SpringComponent
public class ServicioForm extends BaseFormEntity<Servicio> {


    @Autowired
    private ImpuestoRepositorio impuestoRepositorio;

    private MTextField codigo = new MTextField("Codigo:").withWidth("100%");
    private MTextField nombre= new MTextField("Nombre:").withWidth("100%");
    private TextArea descripcion = new TextArea("Descripcion");

    private TwinColSelect<Impuesto> impuestosx = new TwinColSelect<>();


    public ServicioForm() {
        super(Servicio.class);
        setModalWidth("60%");
        setModalHeight("-1px");
        setModalWindowTitle("Servicio");
    }

    @Override
    public void save(Servicio servicio)
    {
        servicio.setImpuestos(new ArrayList<>( impuestosx.getSelectedItems() ));
        super.save(servicio);
    }

    @Override
    public void setEntity(Servicio p) {
        List<Impuesto> impuestos = impuestoRepositorio.findAll();
        List<Impuesto> selecctionado = new ArrayList<>();

        if (p.getImpuestos() != null && p.getImpuestos().size() > 0) {
            for (Impuesto impuesto : p.getImpuestos()) {
                selecctionado.add(impuestos.stream().filter(x -> x.getId() == impuesto.getId()).findFirst().get());
            }
        }
        impuestosx.setItems(impuestos);
        impuestosx.setValue(new HashSet<>(selecctionado));
        super.setEntity(p);
    }

    @Override
    protected Component createContent() {
        descripcion.setWidth("100%");
        impuestosx.setItemCaptionGenerator(impuesto ->
                impuesto.getNombre() + " " +impuesto.getTarifa().setScale(2, BigDecimal.ROUND_HALF_UP));
        impuestosx.addSelectionListener(multiSelectionEvent -> getBinder().validate());
        impuestosx.setWidth("100%");
        return getDefaultLayoutContent(new MFormLayout(
                codigo,
                nombre,
                descripcion,
                new MHorizontalLayout(impuestosx).withCaption("Impuestos:").withWidth("100%")
        ).withWidth("100%"));
    }
}
