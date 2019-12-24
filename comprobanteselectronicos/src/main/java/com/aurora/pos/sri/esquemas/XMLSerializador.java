package com.aurora.pos.sri.esquemas;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by max on 5/17/2017.
 */
public class XMLSerializador<T> {

    private Class<T> type;

    public XMLSerializador(Class<T> type)
    {
        this.type = type;
    }

    public void serializar(T object,File file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(this.type);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(object,file);
    }

    public ByteArrayOutputStream serializar(T object) throws JAXBException, IOException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(this.type);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        marshaller.marshal(object,output);

        return output;
    }

}
