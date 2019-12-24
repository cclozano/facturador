package com.aurora.pos.server.ui.view;

import com.aurora.pos.entidades.RespuestaAutorizacion;
import com.aurora.pos.sri.api.Autorizador;
import com.aurora.pos.sri.esquemas.factura_v1.Factura;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;


@SpringComponent
@PrototypeScope
public class ConsultaFacturasCompra extends Window{


    private VerticalLayout maintLayout = new VerticalLayout();
    private TextField claveAccesoText = new TextField();
    private Button consultarButton = new Button(FontAwesome.SEARCH);
    private Button saveFactura = new Button(FontAwesome.DOWNLOAD);

    private TextArea textArea = new TextArea();


    @Autowired
    @Qualifier("autorizadorProduccion")
    private Autorizador autorizadorProduccion;

    @Autowired
    protected EventBus.ViewEventBus eventBus;


    Factura factura;


    public ConsultaFacturasCompra()
    {
        maintLayout.setWidth("100%");
        maintLayout.setHeight("100%");
        maintLayout.setMargin(true);
        maintLayout.setSpacing(true);
        saveFactura.setEnabled(false);
        claveAccesoText.setPlaceholder("Clave Acceso...");
        HorizontalLayout horizontalLayout = new
                MHorizontalLayout(claveAccesoText,consultarButton,saveFactura)
                .expand(claveAccesoText).withWidth("100%").withSpacing(true);

        textArea.setWidth("100%");
        textArea.setHeight("100%");
        maintLayout.addComponent(horizontalLayout);
        maintLayout.addComponent(textArea);
        maintLayout.setExpandRatio(textArea,1);


        consultarButton.addClickListener(clickEvent -> {

            Factura factura = consultarFactura(claveAccesoText.getValue());
            if(factura==null)
            {
                Notification.show("Mensaje","Factura no encontrada", Notification.Type.HUMANIZED_MESSAGE);
                return;
            }

           this. factura = factura;
           this.saveFactura.setEnabled(true);

        });

        saveFactura.addClickListener(clickEvent -> {

            ConsultaFacturaModifiedEvent evento = new ConsultaFacturaModifiedEvent(this.factura);
            eventBus.publish(this,evento);
            UI.getCurrent().removeWindow(this);
        });



        this.setWidth("70%");
        this.setHeight("80%");
        this.center();
        this.setModal(true);
        this.setCaption("Consultar Comprobante");
        this.setContent(maintLayout);
    }

    public void show()
    {
        this.textArea.setValue("");
        this.factura = null;
        this.saveFactura.setEnabled(false);
        this.claveAccesoText.setValue("");
        UI.getCurrent().addWindow(this);
    }


    private Factura consultarFactura(String codigo)
    {
        RespuestaAutorizacion respuesta =   autorizadorProduccion.consultarAutorizacion(codigo);

        try {
            JAXBContext context = JAXBContext
                    .newInstance(Factura.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            if(respuesta ==null || respuesta.getComprobante() ==null || respuesta.getComprobante().isEmpty())
            {

                return null;
            }

            textArea.setValue(respuesta.getComprobante());
            StringReader reader = new StringReader(respuesta.getComprobante());
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(reader);
            Factura factura  = (Factura)  unmarshaller.unmarshal(xmlStreamReader);
            String xml = identar(respuesta.getComprobante());
            textArea.setValue(xml);
            return factura;

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }



    private String identar(String xml)
    {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                    document,
                    XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);

            transformer.transform(new DOMSource(document), streamResult);

            return stringWriter.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
