package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.impuestos.entidades.FacturaCompra;
import com.aurora.impuestos.entidades.TipoFacturaCompra;
import com.aurora.impuestos.repositorios.TipoFacturaCompraRepositorio;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.v7.fields.MDateField;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;


@ViewScope
@SpringComponent
public class FacturaCompraForm extends BaseFormEntity<FacturaCompra> {


    private MTextField numero =new MTextField("Numero Factura:").withWidth("100%");
    private MTextField ruc =new MTextField("Ruc:").withWidth("100%");
    private MTextField nombre =new MTextField("Nombre:").withWidth("100%");
    private MTextField claveAcceso = new MTextField("Clave Acceso").withWidth("100%");
    private MTextField subtotal12 =new MTextField("Sub Total 12%:").withWidth("100%");
    private MTextField subtotal0 =new MTextField("Sub Total 0%:").withWidth("100%");
    private ComboBox<TipoFacturaCompra> tipoFacturaCompra = new ComboBox<>("Tipo Gasto");


   private MTextField totalIva = new MTextField("Iva:").withWidth("100%").withEnabled(false);
   private MTextField xxx = new MTextField("Otros Impuestos").withWidth("100%").withEnabled(false);
   private MTextField totalFac = new MTextField("Total Factura").withWidth("100%").withEnabled(false);

   private DateField fecha = new DateField("Fecha:");

   @Autowired
    private TipoFacturaCompraRepositorio tipoFacturaCompraRepositorio;

    public FacturaCompraForm() {
        super(FacturaCompra.class);
        setModalWindowTitle("Factura Compra");
        setModalWidth("60%");
        getBinder().addValueChangeListener(valueChangeEvent -> {
            BigDecimal iva = getBinder().getBean().getIva();
           if(iva==null)
                totalIva.setValue("0");
            else
                totalIva.setValue(iva.toString());

            BigDecimal total= getBinder().getBean().getTotalFactura();
           if(total==null)
                totalFac.setValue("0");
            else
                totalFac.setValue(total.toString());

            BigDecimal otrosImpuestos = getBinder().getBean().getTotalOtrosImpuestos();
            if(otrosImpuestos ==null)
                xxx.setValue("0");
            else
                xxx.setValue(otrosImpuestos.toString());
        });
    }

    @Override
    protected void bind() {

        getBinder().forField(subtotal12)
                .withConverter(new StringToBigDecimalConverter("Solo Numeros")).bind("subtotal12");

        getBinder().forField(subtotal0)
                .withConverter(new StringToBigDecimalConverter("Solo Numeros")).bind("subtotal0");

        getBinder().forField(fecha).withConverter(new LocalDateToDateConverter()).bind("fecha");

        try {
            super.bind();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void setEntity(FacturaCompra entity) {
        this.totalFac.setValue(entity.getTotalFactura().toString());
        this.totalIva.setValue(entity.getIva().toString());
        this.xxx.setValue(entity.getTotalOtrosImpuestos().toString());
        setModalWidth("60%");
        setModalWindowTitle("Tipo Gasto");
        super.setEntity(entity);
    }

    @Override
    protected Component createContent() {
        fecha.setWidth("100%");
        fecha.setValue(LocalDate.now());
        tipoFacturaCompra.setWidth("100%");
        tipoFacturaCompra.setItems(tipoFacturaCompraRepositorio.findAll());
        tipoFacturaCompra.setItemCaptionGenerator(tipo ->
          "[" + tipo.getTipoGasto() + "] " + tipo.getNombre()
        );


        return new MFormLayout(numero,fecha,ruc,nombre,tipoFacturaCompra, claveAcceso,subtotal0,subtotal12,totalIva,xxx ,totalFac,getToolbar())
                .withMargin(true).withHeightUndefined().withWidth("100%");
    }


    public void enableAceptar()
    {
        this.getSaveButton().setEnabled(true);
    }
}
