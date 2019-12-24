package com.aurora.util;

import com.aurora.framework.data.BaseEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.dialogs.ConfirmDialog;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by cmendoza on 27/3/2017.
 */
public abstract class BaseCrud<E extends BaseEntity> extends VerticalLayout implements View {

    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    protected JpaRepository<E, Long> repository;

    protected Grid<E> dataGrid;

    protected ToolBar toolBar = new ToolBar();
    protected Button newButton ;
    protected Button delButton ;
    protected Button viewButton ;
    protected Button editButton ;

    public BaseCrud() {
        this.dataGrid = getDataGrid();
        this.setWidth("100%");
        this.setHeight("100%");
        this.dataGrid.setWidth("100%");
        this.dataGrid.setHeight("100%");
        this.addComponents( toolBar,this.dataGrid);
        this.setExpandRatio(this.dataGrid,1);

        this.toolBar.addDerecha(getNewButton());
        this.toolBar.addDerecha(getViewButton());
        this.toolBar.addDerecha(getEditButton());
        this.toolBar.addDerecha(getDeleteButton());
    }



    public Button getNewButton() {
        if (this.newButton == null) {
            this.newButton = new Button(FontAwesome.FILE);
            this.newButton.setDescription("Nuevo");
            this.newButton.addClickListener(clickEvent -> {
                AbstractForm<E> windows = getForm(getNew(), AbstractForm.Tipo.NEW);
                windows.setSavedHandler(var1 -> {
                            try {
                                save(windows.getEntity());
                                windows.hideWindows();
                                Notification.show("Nuevo Registro Creado");
                                this.dataGrid.setItems(getList());
                            } catch (Exception e) {
                                Notification.show("Error",
                                        "Ha ocurriodo un error al guardar: " + e.getMessage(),
                                        Notification.Type.ERROR_MESSAGE);
                            }
                        }
                );
                windows.openInModalPopup();
            });
        }
        return this.newButton;
    }


    public Button getDeleteButton() {
        if (this.delButton == null) {
            this.delButton = new Button(FontAwesome.TRASH);
            this.delButton.setDescription("Eliminar");
            this.delButton.addClickListener(clickEvent -> {
                Set<E> selected = this.dataGrid.getSelectedItems();
                if (selected.isEmpty()) {
                    Notification.show("Escoja un elemento");
                    return;
                }

                ConfirmDialog.show(UI.getCurrent(), "Eliminar", "Desea eliminar el registro",
                        "Aceptar", "Cancelar", confirmDialog -> {
                            if (confirmDialog.isConfirmed()) {
                                try {
                                    this.delete(selected.stream().findFirst().get());
                                    this.dataGrid.setItems(getList());
                                } catch (Exception e) {
                                    Notification.show("Error", "Error al eliminar:" + e.getMessage(), Notification.Type.ERROR_MESSAGE);
                                }
                            }
                        });
            });
        }
        return this.delButton;
    }


    public Button getViewButton() {
        if (this.viewButton == null) {
            this.viewButton = new Button(FontAwesome.EYE);
            this.viewButton.addClickListener(clickEvent -> {
                Set<E> selected = this.dataGrid.getSelectedItems();
                if (selected.isEmpty()) {
                    Notification.show("Escoja un elemento");
                    return;
                }
                AbstractForm form = getForm(selected.stream().findFirst().get(), AbstractForm.Tipo.VIEW);
                form.openInModalPopup();
            });
        }
        return this.viewButton;
    }

    public Button getEditButton() {
        if (editButton == null) {
            this.editButton = new Button(FontAwesome.EDIT);
            this.editButton.addClickListener(clickEvent -> {
                SingleSelectionModel<E> singleSelect =
                        (SingleSelectionModel<E>) dataGrid.getSelectionModel();
                AbstractForm<E> windows = getForm(singleSelect.getSelectedItem().get(), AbstractForm.Tipo.EDIT);
                windows.setModifyHandler(var1 -> {
                    try {
                        edit(windows.getEntity());
                        windows.hideWindows();
                        Notification.show("Registro Modificado");
                        this.dataGrid.setItems(getList());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                windows.openInModalPopup();
            });
        }

        return this.editButton;
    }


    public abstract E getNew();

    public abstract AbstractForm<E> getForm(E entity, AbstractForm.Tipo tipo);

    public abstract Grid<E> getDataGrid();

    public void save(E entity) throws Exception {
        this.repository.save(entity);
    }


    public void delete(E entity) throws Exception {
        this.repository.delete(entity);
    }


    public void edit(E entity) throws Exception {
        this.repository.save(entity);
    }


    public abstract List<E> getList();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.dataGrid.setItems(getList());
    }
}
