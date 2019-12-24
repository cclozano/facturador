package com.aurora.pos.server.seguridad;

import com.aurora.pos.server.ui.view.crud.ProvinciaCrudView;
import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

@Component
public class Max implements ViewInstanceAccessControl {
    @Override
    public boolean isAccessGranted(UI ui, String s, View view) {

        return true;
    }
}
