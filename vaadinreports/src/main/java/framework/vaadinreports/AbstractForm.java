package framework.vaadinreports;



import com.vaadin.data.BeanValidationBinder;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.Iterator;

public abstract class AbstractForm<T> extends CustomComponent {

    private Window popup = new Window();
    private boolean eagerValidation = true;
    private BeanValidationBinder<T> binder;
    private Button resetButton;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private Button cancelButton;
    private T entity;

    private AbstractForm.SavedHandler<T> savedHandler;
    private AbstractForm.ModifyHandler<T> modifyHandler;
    private AbstractForm.ResetHandler<T> resetHandler;
    private AbstractForm.DeleteHandler<T> deleteHandler;

    //private String title;
    private Tipo tipo;

    public AbstractForm(String title, Tipo tipo) {

        this.tipo = tipo;
        this.popup = new Window(title);
        this.addAttachListener( event -> {
                AbstractForm.this.lazyInit();
                AbstractForm.this.adjustResetButtonState();
        });
    }

    protected void lazyInit() {
        if(this.getCompositionRoot() == null) {

            Component component = this.createContent();
            component.setEnabled(true);
            if (this.tipo == Tipo.VIEW){
            component.setEnabled(false);}
            VerticalLayout layout = new VerticalLayout(
                    new Label(),
                    getToolbar(),
                    component

            );
            layout.setSpacing(false);
            layout.setWidth("100%");
            layout.setHeight("-1px");
            layout.setMargin(new MarginInfo(false,true,true,true));

            this.setCompositionRoot(layout);
            this.adjustSaveButtonState();
            this.adjustEditButtonState();
            this.adjustResetButtonState();
        }
    }

    protected void adjustSaveButtonState() {
        if(this.isEagerValidation() && this.isBound()) {
            this.getSaveButton().setEnabled(this.binder.isValid());
        }

    }

    protected void adjustEditButtonState(){
        if(this.isEagerValidation() && this.isBound()) {
            this.getEditButton().setEnabled(this.binder.isValid());
        }
    }

    protected boolean isBound() {
        return this.binder != null;
    }

    public boolean isEagerValidation() {
        return eagerValidation;
    }

    public void setEagerValidation(boolean eagerValidation) {
        this.eagerValidation = eagerValidation;
    }

    public Button getSaveButton() {
        if(this.saveButton == null) {
            this.setSaveButton(this.createSaveButton());
        }

        return this.saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
        saveButton.addClickListener(clickEvent ->  save());
    }

    public Button getEditButton() {
        if (this.editButton== null){
            this.setEditButton(this.createEditButton());
        }
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
        editButton.addClickListener(clickEvent -> modify());
    }

    protected void save() {
        try {
            this.savedHandler.onSave(this.getEntity());
            //this.getFieldGroup().setBeanModified(false);
            this.adjustResetButtonState();
            this.adjustSaveButtonState();
            this.adjustEditButtonState();
        }catch (Exception ex)
        {
            Notification.show("Error Guardar",
                    "Ha Ocurrido un error al guardar: " + ex.getMessage(),
                    Notification.Type.ERROR_MESSAGE
                    );
        }
    }

    protected void modify(){
        this.modifyHandler.onModify(this.getEntity());
        this.adjustResetButtonState();
        this.adjustSaveButtonState();
        this.adjustEditButtonState();
    }


    protected void adjustResetButtonState() {
        if(this.popup != null && this.popup.getParent() != null) {
            this.getResetButton().setEnabled(true);
        } else {
            if(this.isEagerValidation() && this.isBound()) {
               // boolean modified = this.fieldGroup.isBeanModified();
                boolean modified = false;
                this.getResetButton().setEnabled(modified || this.popup != null);
            }

        }
    }

    public BeanValidationBinder<T> setEntity(T entity) {
        this.entity = entity;
        this.binder = new BeanValidationBinder(entity.getClass());
        return this.binder;
     }

    public Button getResetButton() {
        if(this.resetButton == null) {
            this.setResetButton(this.createCancelButton());
        }
        return this.resetButton;
    }

    public Button getCancelButton()
    {
        if(this.cancelButton==null) {
            this.cancelButton = new Button(FontAwesome.STOP);
            this.cancelButton.setEnabled(true);
            this.cancelButton.addClickListener(clickEvent ->{
                        ConfirmDialog.show(UI.getCurrent(),"Cerrar",
                                "Desea salir sin guardar?",
                                "Aceptar","Cancelar",
                                confirmDialog -> {
                                    if(confirmDialog.isConfirmed())
                                    {
                                        hideWindows();
                                    }
                                });


                    });
        }
        return this.cancelButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
        this.resetButton.addClickListener((Button.ClickListener) event -> AbstractForm.this.reset());
    }


    protected void reset() {
        this.resetHandler.onReset(this.getEntity());
        this.adjustResetButtonState();
        this.adjustSaveButtonState();
        this.adjustEditButtonState();
    }

    protected Button createCancelButton() {
        Button cancelButton = new Button(FontAwesome.STOP);
        cancelButton.setDescription("Salir");
        cancelButton.setVisible(false);
        return cancelButton;
    }


    protected Button createSaveButton() {
        Button button = new Button(FontAwesome.SAVE);
        button.setVisible(this.tipo == Tipo.NEW);
        return button;
    }

    protected Button createEditButton(){
        Button button = new Button(FontAwesome.SAVE);
        button.setVisible(this.tipo == Tipo.EDIT);
        return button;
    }

    public Window openInModalPopup() {
        //this.popup = new Window(title, this);
        this.popup.setContent(this);
        //this.popup.setCaption(title);
        this.popup.setModal(true);
        UI.getCurrent().addWindow(this.popup);
        this.focusFirst();
        return this.popup;
    }

    public void hideWindows()
    {
        if(this.popup!=null)
            UI.getCurrent().removeWindow(this.popup);
    }

    public void focusFirst() {
        Component compositionRoot = this.getCompositionRoot();
        this.findFieldAndFocus(compositionRoot);
    }

    private boolean findFieldAndFocus(Component compositionRoot) {
        if(compositionRoot instanceof AbstractComponentContainer) {
            AbstractComponentContainer cc = (AbstractComponentContainer)compositionRoot;
            Iterator i$ = cc.iterator();

            while(i$.hasNext()) {
                Component component = (Component)i$.next();
                if(component instanceof AbstractTextField) {
                    AbstractTextField abstractField1 = (AbstractTextField)component;
                    abstractField1.selectAll();
                    return true;
                }

                if(component instanceof AbstractField) {
                    AbstractField abstractField = (AbstractField)component;
                    abstractField.focus();
                    return true;
                }

                if(component instanceof AbstractComponentContainer && this.findFieldAndFocus(component)) {
                    return true;
                }
            }
        }

        return false;
    }

    public HorizontalLayout getToolbar() {
        ToolBar toolBar = new ToolBar();
        toolBar.addDerecha(this.getSaveButton());
        toolBar.addDerecha(this.getEditButton());
        toolBar.addDerecha(this.getResetButton());
        toolBar.addDerecha(this.getDeleteButton());
        toolBar.addDerecha(this.getCancelButton());
        //toolBar.setMargin(new MarginInfo(fa,false,false,false));
        return toolBar;
       // return new ToolBar(new Component[]{this.getSaveButton(),this.getEditButton(), this.getResetButton(), this.getDeleteButton(),getCancelButton()});
    }

    public Button getDeleteButton() {
        if(this.deleteButton == null) {
            this.setDeleteButton(this.createDeleteButton());
        }
        return this.deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
        deleteButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                ConfirmDialog.show(UI.getCurrent()
                        ,"Borrar Registro","Esta seguro de Eliminar?",
                        "Aceptar","Cancelar",confirmDialog -> {
                          if(confirmDialog.isConfirmed())
                          {
                              deleteHandler.onDelete(getEntity());
                          }
                        });

            }
        });
    }

    public BeanValidationBinder<T> getBinder() {
        return binder;
    }

    protected Button createDeleteButton() {
        Button button = new Button("Eliminar");
        button.setVisible(this.tipo == Tipo.DELETE);
        return button;
    }

    public void setSavedHandler(SavedHandler<T> savedHandler) {
        this.savedHandler = savedHandler;
    }

    public void setResetHandler(ResetHandler<T> resetHandler) {
        this.resetHandler = resetHandler;
    }

    public void setDeleteHandler(DeleteHandler<T> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    public void setModifyHandler(ModifyHandler<T> modifyHandler) {
        this.modifyHandler = modifyHandler;
    }

    public T getEntity() {
        return this.entity;
    }

    protected abstract Component createContent();

    public interface DeleteHandler<T> {
        void onDelete(T var1);
    }

    public interface ResetHandler<T> {
        void onReset(T var1);
    }

    public interface SavedHandler<T> {
        void onSave(T var1);
    }

public interface ModifyHandler<T>{
        void onModify(T var1);
}
    public enum Tipo
    {
        NEW,DELETE,VIEW, EDIT
    }


    @Override
    public void setWidth(String width)
    {
        this.popup.setWidth(width);
    }


    @Override
    public void setHeight(String height)
    {
        popup.setHeight(height);
    }
}