package com.aurora.pos.server.ui.view.crud.forms;

import com.aurora.framework.vaadinext.crud.BaseFormEntity;
import com.aurora.pos.entidades.Rol;
import com.aurora.seguridad.entidades.Usuario;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;


import com.vaadin.ui.PasswordField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;



@ViewScope
@SpringComponent
public class UsuarioForm extends BaseFormEntity<Usuario>
{

    private MTextField email = new MTextField("Correo").withWidth("100%");
    private PasswordField password = new PasswordField();
    private MTextField name = new MTextField("Nombre:").withWidth("100%");
    private CheckBox locked = new CheckBox("Bloqueado");
    private ComboBox<String> rol = new ComboBox<String>("Rol");

    @Autowired
    PasswordEncoder passwordEncoder;


    private boolean passworModificado = false;

    public UsuarioForm() {
            super(Usuario.class);
            setModalWindowTitle("Usuario");
            setModalWidth("50%");
            password.addValueChangeListener(valueChangeEvent ->{
                if(!valueChangeEvent.getOldValue().isEmpty())
                    passworModificado =true;


            });
            rol.setItems(Rol.getAllRoles());
    }

    @Override
    protected void save(Usuario entity) {
        if(passworModificado)
            entity.setPassword(   passwordEncoder.encode(entity.getPassword())  );

        super.save(entity);
    }

    @Override
    protected Component createContent() {
        password.setCaption("Clave:");
        password.setWidth("100%");
        return getDefaultLayoutContent(new MFormLayout(email,password,name,rol,locked).withWidth("100%"));
    }


}
