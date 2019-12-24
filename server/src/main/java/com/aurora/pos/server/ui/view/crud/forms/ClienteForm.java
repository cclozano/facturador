package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.framework.vaadinext.crud.EntityModifiedEvent;
import com.aurora.pos.entidades.Cliente;
import com.aurora.pos.entidades.TipoIdentificacion;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;

/**
 * Created by max on 23/08/17.
 */

@ViewScope
@SpringComponent
public class ClienteForm extends BaseFormEntity<Cliente> {

    private static final long serialVersionUID = 1L;



    TextField nombre = new MTextField("Nombre:").withWidth("100%");
    TextArea direccion = new TextArea("Direccion:");
    TextField telefono = new MTextField("Telefono:").withWidth("100%");
    TextField identificacion = new MTextField("Identificacion").withWidth("100%");
    TextField correo = new MTextField("Correo:").withWidth("100%");
    ComboBox<TipoIdentificacion> tipoIdentificacion = new ComboBox<>("Tipo Identificacion:");

    public ClienteForm() {
        super(Cliente.class);

        setModalWidth("70%");
        setModalWindowTitle("Cliente");
    }

    @Override
    protected Component createContent() {
        direccion.setWidth("100%");
        tipoIdentificacion.setWidth("100%");
        tipoIdentificacion.setItems(TipoIdentificacion.values());

        return getDefaultLayoutContent(new MFormLayout(
                nombre,
                identificacion,
                tipoIdentificacion,
                correo,
                telefono,
                direccion
        ).withWidth("100%"));
    }

    /*protected void save(Cliente entity)
    {
        repository.save(entity);
        eventBus.publish(this, new ClientModifiedEvent(entity));
    }*/

    @Override
    public EntityModifiedEvent<Cliente> getModifiedEven(Cliente entity) {
        return new ClientModifiedEvent(entity);
    }




}
