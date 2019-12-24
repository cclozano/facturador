package com.aurora.pos.server.ui.view;


import com.aurora.config.ParametrosServer;
import com.aurora.config.Sections;
import com.aurora.pos.entidades.RespuestaAutorizacion;
import com.aurora.pos.server.ui.view.facturar.FacturarView;
import com.aurora.pos.sri.api.Autorizador;
import com.aurora.pos.sri.esquemas.factura_v1.Factura;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@SpringView(name = TestView.VIEW_NAME)
//@SideBarItem(sectionId = Sections.TRANSACCIONES,caption = "TEST",order = 2)
//@VaadinFontIcon(VaadinIcons.TEETH)
public class TestView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "testView";
    public Button print = new Button("PRINT");
    public TextArea textArea = new TextArea();


    @Autowired
    private ParametrosServer parametrosServer;

    @Autowired
    @Qualifier("autorizadorProduccion")
    private Autorizador autorizador;

    private TextField txtCodigo = new TextField();


    public TestView()
    {
        this.setWidth("100%");
        this.setHeight("100%");
        addComponent(txtCodigo);
        txtCodigo.setWidth("100%");
        addComponent(print);

        print.addClickListener(clickEvent -> {
            RespuestaAutorizacion repuesta =   autorizador.consultarAutorizacion(txtCodigo.getValue());

            try {
                JAXBContext context = JAXBContext
                        .newInstance(Factura.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                textArea.setValue(repuesta.getComprobante());
                StringReader reader = new StringReader(repuesta.getComprobante());
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(reader);
                Factura factura  = (Factura)  unmarshaller.unmarshal(xmlStreamReader);
                Notification.show(factura.getId());

            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

        });

        textArea.setWidth("100%");
        textArea.setHeight("100%");
        addComponent(textArea);
        setExpandRatio(textArea,1);

    }
}
