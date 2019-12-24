package com.aurora.pos.server.seguridad;

import com.aurora.pos.server.ui.view.crud.ProvinciaCrudView;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

@Component
public class AccessControlView implements ViewAccessControl {
    @Override
    public boolean isAccessGranted(UI ui, String s) {

        return true;
    }
}
