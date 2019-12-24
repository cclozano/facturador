package com.aurora.pos.server;

import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.servicios.ServicioEmisor;
import com.aurora.ui.EmisorConfigForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.spring.sidebar.components.ValoSideBar;

import java.util.Locale;
import java.util.TimeZone;

@SpringUI
@Title("Facturacion Online")
@SpringViewDisplay
@Theme("tests-valo-dark")
//@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ServerUI extends UI implements ViewDisplay {


    @Autowired
    ValoSideBar sideBar;

    @Autowired
    private EmisorConfigForm emisorConfigForm;

    @Autowired
    private ServicioEmisor servicioEmisor;

    private Panel content;

    private HorizontalLayout rootLayout;

    public ServerUI() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
        VaadinSession.getCurrent().setLocale(Locale.US);
        content = new Panel();
        content.setWidth("100%");
        content.setHeight("100%");
        rootLayout = new HorizontalLayout();
        rootLayout.setWidth("100%");
        rootLayout.setHeight("100%");

    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getNavigator().setErrorView(new ErrorView());
        getNavigator().addView("",new BlancPage());
        Emisor emisor = this.servicioEmisor.getEmisor();
        if(emisor == null) {
            Notification.show("Ingrese los datos del contribuyente",
                    Notification.Type.WARNING_MESSAGE);
            this.emisorConfigForm.setEntity(new Emisor());
            this.emisorConfigForm.openInModalPopup();
            this.emisorConfigForm.setOnEditEvent(emisor1 -> {
                inicializarInterfazUsuario();
            });
        }
        else {
            inicializarInterfazUsuario();
            this.emisorConfigForm.setOnEditEvent(null);
        }
    }

    private void inicializarInterfazUsuario()
    {
        rootLayout.addComponents(sideBar, content);
        rootLayout.setExpandRatio(content, 1f);
        setContent(rootLayout);
        CssLayout header = new CssLayout();
        MenuBar menuBar = new MenuBar();
        header.addComponent(menuBar);
        header.addComponent(menuBar);
        MenuBar.MenuItem settingsItem = menuBar.addItem("", FontAwesome.USER, null);
        MenuBar.MenuItem useLargeIconsItem = settingsItem.addItem("Reducir", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                sideBar.setLargeIcons(selectedItem.isChecked());
            }
        });
        useLargeIconsItem.setCheckable(true);
        MenuBar.MenuItem showLogoItem = settingsItem.addItem("Mostrar Logo", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                if (selectedItem.isChecked()) {
                    showLogo();
                } else {
                    hideLogo();
                }
            }
        });
        MenuBar.MenuItem showConfigForm = settingsItem.addItem("Configuracion", menuItem -> {
            this.emisorConfigForm.openInModalPopup();
        });
        showLogoItem.setCheckable(true);
        MenuBar.MenuItem logoutMenuItem = settingsItem.addItem("Cerrar Sesion", menuItem -> {

            ConfirmDialog confirmDialog = new ConfirmDialog();

            confirmDialog.show(UI.getCurrent(), "Salir", "Desea cerrar sesion?",
                    "Ok", "Cancelar", x -> {
                        if (x.isConfirmed()) {
                            UI ui = getUI();
                            ui.getSession().getSession().invalidate();
                            ui.getPage().reload();
                        }
                    });
        });
        sideBar.setHeader(header);
    }







    private void showLogo() {
        sideBar.setLogo(new Label(FontAwesome.ROCKET.getHtml(), ContentMode.HTML));
    }


    private void hideLogo() {
        sideBar.setLogo(null);
    }

    @Override
    public void showView(View view) {
       content.setContent((Component)view);
    }

    private class ErrorView extends VerticalLayout implements View {

        private static final long serialVersionUID = -1349484555495574658L;
        private Label message;

        ErrorView() {
            setMargin(true);
            message = new Label();
            addComponent(message);
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            message.setValue(String.format("No existe la vista: %s", event.getViewName()));
        }
    }

    private class BlancPage extends VerticalLayout implements View {

        private static final long serialVersionUID = -1349484555495574658L;
        private Label message;

        BlancPage() {
            setMargin(true);
            message = new Label();
            addComponent(message);
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            message.setValue(String.format("Sistema Comprobantes Electronicos Online"));
        }
    }









}
