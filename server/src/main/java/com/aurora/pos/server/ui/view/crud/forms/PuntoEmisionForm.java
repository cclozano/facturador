package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Establecimiento;
import com.aurora.pos.entidades.PuntoEmision;
import com.aurora.pos.repositorios.EstablecimientoRepositorio;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

import java.util.List;

@ViewScope
@SpringComponent
public class PuntoEmisionForm  extends BaseFormEntity<PuntoEmision> {

    private MTextField codigo = new MTextField("Código").withWidth("100%");
    private MTextField descripcion = new MTextField("Descripción").withWidth("100%");
    private MTextField numeroUtimaFactura = new MTextField("Numero Ultima Factura:").withWidth("100%");
    private ComboBox<Establecimiento> establecimiento = new ComboBox<>("Establecimiento");



    @Autowired
    public PuntoEmisionForm(EstablecimientoRepositorio establecimientoRepositorio) {
        super(PuntoEmision.class);
        setModalWidth("60%");
        setModalWindowTitle("Punto de Emisión");

        establecimiento.setItems(establecimientoRepositorio.findAll());
        establecimiento.setWidth("100%");
        establecimiento.setItemCaptionGenerator(prop -> prop.getCodigo() + " " + prop.getNombreComercial());
    }

    @Override
    protected void bind() {

        getBinder().forField(numeroUtimaFactura)
                .withConverter(new StringToLongConverter("Solo numero entero")).bind("numeroUtimaFactura");

        super.bind();
    }

    @Override
    protected Component createContent() {


        codigo.setWidth("100%");
        descripcion.setWidth("100%");

        return getDefaultLayoutContent(new MFormLayout(
                codigo,
                descripcion,
                numeroUtimaFactura,
                establecimiento
        ).withWidth("100%"));
    }
}
