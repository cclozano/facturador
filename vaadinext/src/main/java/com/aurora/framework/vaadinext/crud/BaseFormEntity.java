package com.aurora.framework.vaadinext.crud;

import com.aurora.framework.data.BaseEntity;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 * Created by max on 23/08/17.
 */
public abstract class BaseFormEntity<E extends BaseEntity> extends AbstractForm<E> implements EntityForm<E> {

    private static final Logger logger = LoggerFactory.getLogger(BaseFormEntity.class);

    @Autowired
    protected EventBus.ViewEventBus eventBus;

    @Autowired
    protected JpaRepository<E,Long> repository;


    private String modalWidth = null;
    private String modalHeight = null;


    public BaseFormEntity(Class<E> entityType) {
        super(entityType);
        setSaveCaption("Guardar");
        setCancelCaption("Cancelar");
        setSavedHandler(e -> save(e));
        setResetHandler();

    }

    protected void save(E entity)
    {
        try {
            repository.save(entity);
            eventBus.publish(this, getModifiedEven(entity));
            if(editEndListener != null) editEndListener.editEnd(entity);
        }
        catch (ConstraintViolationException ex)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation item :   ex.getConstraintViolations())
            {
                String mensaje = item.getPropertyPath() + " "+item.getMessage();
                stringBuilder.append(mensaje + "\n");
                System.err.println(mensaje);
            }

            Notification.show("Error",stringBuilder.toString(), Notification.Type.TRAY_NOTIFICATION);
        }
        catch (Exception ex)
        {
            Notification notification = new Notification("Error","Error al guardar:" + ex.getMessage(),
                    Notification.Type.TRAY_NOTIFICATION);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(6000);
            logger.error(ex.getLocalizedMessage(),ex);
            notification.show(Page.getCurrent());
        }
    }


    private EditEndListener<E> editEndListener;
    public void addEditEndListener(EditEndListener<E> listener) {
        this.editEndListener = listener;
    }


    public void setResetHandler()
    {
        setResetHandler(entity -> eventBus.publish(this, getModifiedEven(entity) ));
    }



   // public abstract EntityModifiedEvent<E> getModifiedEven(E entity);

    @Override
    public Window openInModalPopup() {
        Window window = super.openInModalPopup();
        if(modalWidth !=null)
            window.setWidth(modalWidth);
        if(modalHeight !=null)
            window.setHeight(modalHeight);
        return window;
    }


    public EntityModifiedEvent<E> getModifiedEven(E entity) {
        return new EntityModifiedEvent<>(entity);
    }


    public MVerticalLayout getDefaultLayoutContent(Component component)
    {
        return new MVerticalLayout(
                component,
                getToolbar()
        ).withWidth("100%");
    }



    public void setModalWidth(String modalWidth) {
        this.modalWidth = modalWidth;
    }

    public void setModalHeight(String modalHeight) {
        this.modalHeight = modalHeight;
    }
}
