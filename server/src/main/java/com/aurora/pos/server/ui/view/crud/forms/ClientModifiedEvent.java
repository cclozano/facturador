package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.EntityModifiedEvent;
import com.aurora.pos.entidades.Cliente;

public class ClientModifiedEvent extends EntityModifiedEvent<Cliente>{
    public ClientModifiedEvent(Cliente cliente) {
        super(cliente);
    }
}
