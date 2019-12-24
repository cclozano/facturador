package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.impuestos.entidades.CodigoImpuestoRetencion;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

import java.math.BigDecimal;


@PrototypeScope
@SpringComponent
public class CodigosRetencionFom extends BaseFormEntity<CodigoImpuestoRetencion> {

    private MTextField codigoImpuesto = new MTextField("Codigo Impuesto:").withWidth("50%");
    private MTextField codigoRetencion = new MTextField("Codigo Retencion:").withWidth("50%");
    private TextArea descripcion =  new TextArea("Descripcion:");
    private MTextField porcentaje = new MTextField("% Retencion:").withWidth("50%");




    public CodigosRetencionFom() {
        super(CodigoImpuestoRetencion.class);
        setModalWindowTitle("Codigo Retencion");
        setModalWidth("50%");
    }

    @Override
    protected void bind() {
        getBinder().forField(porcentaje)
                .withConverter(new StringToBigDecimalConverter("Solo numeros"))
                .bind("porcentaje");
        super.bind();
    }

    @Override
    protected Component createContent() {
        descripcion.setWidth("100%");
        return getDefaultLayoutContent(new MFormLayout(codigoImpuesto,codigoRetencion,porcentaje,descripcion)
                .withExpandRatio(descripcion,1).withWidth("100%"));
    }
}
