package com.aurora.ui;

import com.aurora.config.ParametrosServer;
import com.aurora.pos.entidades.CorreoEmisorConfig;
import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.repositorios.EmisorRepositorio;
import com.aurora.pos.repositorios.FacturaRepositorio;
import com.aurora.pos.servicios.ServicioCorreo;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.sound.midi.Receiver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;


@UIScope
@SpringComponent
public class EmisorConfigForm extends AbstractForm<Emisor> {

    @Autowired
    private EmisorRepositorio emisorRepositorio;

    @Autowired
    private ParametrosServer parametrosServer;

    @Autowired
    private ServicioCorreo servicioCorreo;


    private ComboBox<Emisor.Ambiente> ambiente = new ComboBox<>("Ambiente:");
    private MTextField numeroUltimaFacturaDesarrollo = new MTextField("# Ultima Factura Desarrollo:").withWidth("100%");
    private MTextField numeroContribuyenteEspecial = new MTextField("# Contribuyente Especial:").withWidth("100%");
    private CheckBox obligadoContabilidad = new CheckBox("Obligado LLevar Contabilidad:");

    private MTextField razonSocialEmisor = new MTextField("Razon Social:").withWidth("100%");
    private MTextField nombreComercialEmisor = new MTextField("Nombre Comercial:").withWidth("100%");
    private MTextField rucEmisor = new MTextField("Ruc Emisor:").withWidth("100%");
    private TextArea direccionMatriz = new TextArea("Direccion Matriz:");

    private MTextField pathLogo = new MTextField().withWidth("100%").withEnabled(false);
    private Upload uploadLogo = new Upload();
    private MTextField pathkey = new MTextField().withWidth("100%").withEnabled(false);
    private Upload uploadKey = new Upload();
    private PasswordField passwordKey = new PasswordField("Password Key");


    private MTextField host = new MTextField("Host:").withWidth("100%");
    private MTextField  port = new MTextField("Port:").withWidth("100%");
    private MTextField username = new MTextField("Username:").withWidth("100%");
    private PasswordField password = new PasswordField("Password:");
    private CheckBox auth = new CheckBox("Auth:");
    private CheckBox starttlsEnable = new CheckBox("Start TLS Enable:");


    private MTextField asuntoCorreo =new MTextField("Asunto").withWidth("100%");

    private RichTextArea cuerpoCorreo = new RichTextArea("Mensaje:");


    private Button testCorreoButton = new Button(FontAwesome.PAPER_PLANE_O);

    private MTextField correoEnviar = new MTextField().withWidth("50%");


    public EmisorConfigForm() {
        super(Emisor.class);
        setModalWindowTitle("Configuracion de Emisor");
        setSaveCaption("Guardar");
        setSavedHandler(emisor -> {
            this.emisorRepositorio.save(getEntity());
            if(this.onEditEvent != null)
                this.onEditEvent.onEdit(emisor);
            closePopup();
        });
        setResetHandler(emisor -> closePopup());
        testCorreoButton.addClickListener(clickEvent -> {
            if(correoEnviar.getValue()!=null && correoEnviar.getValue().length()>0) {
                String correoTest = correoEnviar.getValue();
                try {
                    servicioCorreo.sendSimpleMessage(correoTest,
                            getEntity().getCorreo().getAsuntoCorreo(),
                            getEntity().getCorreo().getCuerpoCorreo());
                    Notification.show("Correo enviado con exito");
                }
                catch (Exception ex)
                {
                    Notification.show("Error envio correo: " + ex.getMessage());
                }
            }
            else
            {
                Notification.show("Ingrese correo para prueba");
            }
        });

    }

    @Override
    protected void bind() {
        Binder binder = getBinder();
        binder.forField(numeroContribuyenteEspecial)
                .withConverter(new StringToIntegerConverter("Solo numeros")).bind("numeroContribuyenteEspecial");

        binder.forField(numeroUltimaFacturaDesarrollo)
                .withConverter(new StringToIntegerConverter("Solo numeros")).bind("numeroUltimaFacturaDesarrollo");

       binder.forField(host).bind("correo.host");
       binder.forField(port).withConverter(new StringToIntegerConverter("Solo Numeros")).bind("correo.port");
       binder.forField(username).bind("correo.username");
       binder.forField(password).bind("correo.password");
       binder.forField(auth).bind("correo.auth");
       binder.forField(starttlsEnable).bind("correo.starttlsEnable");
       binder.forField(asuntoCorreo).bind("correo.asuntoCorreo");
       binder.forField(cuerpoCorreo).bind("correo.cuerpoCorreo");


        super.bind();
    }

    @Override
    public Window openInModalPopup() {

        List<Emisor> emisores = this.emisorRepositorio.findAll();

        if (emisores.size() == 0) {
            setEntity(new Emisor());
        } else {
            Emisor emisor = emisores.get(0);
            if(emisor.getCorreo()==null)
                emisor.setCorreo(new CorreoEmisorConfig());
            setEntity(emisor);
        }
        Window window = super.openInModalPopup();
        window.setWidth("60%");
        return window;
    }

    @Override
    protected Component createContent() {

        direccionMatriz.setWidth("100%");
        ambiente.setWidth("100%");
        ambiente.setItems(Emisor.Ambiente.values());

        ambiente.addValueChangeListener(valueChangeEvent -> {
            numeroUltimaFacturaDesarrollo.setVisible(valueChangeEvent.getValue() == Emisor.Ambiente.DESARROLLO);
        });

        password.setWidth("100%");


        FormLayout generalLayout = new MFormLayout(ambiente, numeroUltimaFacturaDesarrollo, numeroContribuyenteEspecial,
                obligadoContabilidad, razonSocialEmisor, nombreComercialEmisor, rucEmisor, direccionMatriz);
        generalLayout.setWidth("100%");


        MHorizontalLayout logo = new MHorizontalLayout(uploadLogo, pathLogo)
                .expand(pathLogo).withWidth("100%").withSpacing(true).withCaption("Logo:");

        uploadLogo.setButtonCaption("Logo");
        uploadLogo.setReceiver((fileName, mineType) -> {
            File file = new File(parametrosServer.getPathUpload() + fileName);
            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fop;
        });

        uploadLogo.addFinishedListener(finishedEvent -> {
           pathLogo.setValue(parametrosServer.getPathUpload() + finishedEvent.getFilename());
        });


        MHorizontalLayout key = new MHorizontalLayout(uploadKey, pathkey)
                .expand(pathkey).withWidth("100%").withSpacing(true).withCaption("Key:");
        uploadKey.setButtonCaption("Key");

        uploadKey.setReceiver((fileName, mineType) -> {
            File file = new File(parametrosServer.getPathUpload() + fileName);
            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fop;
        });

        uploadKey.addFinishedListener(finishedEvent -> {
            pathkey.setValue(parametrosServer.getPathUpload() + finishedEvent.getFilename());
        });


        passwordKey.setWidth("100%");
        FormLayout keyLogoLayout = new MFormLayout(logo, key, passwordKey);


        FormLayout mailLayout = new MFormLayout(host,port,username,password,auth,starttlsEnable)
                .withWidth("100%");

        //testCorreoButton.setWidth("100%");
        cuerpoCorreo.setWidth("100%");
        FormLayout testCorreoLayout =
                new MFormLayout(new MHorizontalLayout(correoEnviar,testCorreoButton)
                        .withWidth("100%").expand(correoEnviar)
                        .withSpacing(true).withCaption("Correo Test:"),asuntoCorreo,cuerpoCorreo);


        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(generalLayout, "Datos Generales");
        tabSheet.addTab(keyLogoLayout, "Logo & Token");
        tabSheet.addTab(mailLayout,"Correo");
        tabSheet.addTab(testCorreoLayout,"Test Correo");
        tabSheet.setWidth("100%");
        tabSheet.setHeight("100%");

        return new MVerticalLayout(tabSheet, getToolbar()).withWidth("100%");
    }

    private OnEditEvent onEditEvent;

    public void setOnEditEvent(OnEditEvent onEditEvent) {
        this.onEditEvent = onEditEvent;
    }

    public interface OnEditEvent
    {
        void onEdit(Emisor emisor);
    }


}
