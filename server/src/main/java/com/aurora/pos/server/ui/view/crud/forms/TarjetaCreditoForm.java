package com.aurora.pos.server.ui.view.crud.forms;


import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Banco;
import com.aurora.pos.entidades.TarjetaCredito;
import com.aurora.pos.repositorios.BancoRepositorio;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

@ViewScope
@SpringComponent
public class TarjetaCreditoForm extends BaseFormEntity<TarjetaCredito>{


    private MTextField codigo = new MTextField("Codigo:").withWidth("100%");
    private MTextField nombre = new MTextField("Nombre").withWidth("100%");
    private ComboBox<Banco> bancoEmisor = new ComboBox<>("Banco Emisor");

    @Autowired
    private BancoRepositorio bancoRepositorio;


    public TarjetaCreditoForm() {
        super(TarjetaCredito.class);
        setModalWindowTitle("Tarjeta de Credito");
        setModalWidth("50%");
    }

    @Override
    protected Component createContent() {
        this.bancoEmisor.setItems(bancoRepositorio.findAll());
        this.bancoEmisor.setItemCaptionGenerator(x->x.getNombre());
        this.bancoEmisor.setWidth("100%");
        return getDefaultLayoutContent(new MFormLayout(codigo,nombre,bancoEmisor).withWidth("100%"));
    }
}
