package com.aurora.framework.vaadinext.crud;

import com.aurora.framework.data.BaseEntity;
import com.vaadin.ui.Window;

/**
 * Created by max on 22/08/17.
 */
public interface EntityForm<E extends BaseEntity> {

    void setEntity(E entity);
    Window openInModalPopup();
    void closePopup();
    Window getPopup();
}
