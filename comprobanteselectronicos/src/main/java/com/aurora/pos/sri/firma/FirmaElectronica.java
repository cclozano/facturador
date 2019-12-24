package com.aurora.pos.sri.firma;

import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Enumeration;


/**
 * Created by max on 18/06/17.
 */
public class FirmaElectronica {

    private String passSignature;
    private String pathSignature ;



    public FirmaElectronica(String passSignature, String pathSignature)
    {
        this.passSignature = passSignature;
        this.pathSignature = pathSignature;
    }



    public  byte[]  firmar(ByteArrayOutputStream resource) throws Exception{
        KeyStore keyStore = getKeyStore();
        String alias = getAlias(keyStore);
        X509Certificate certificate = (X509Certificate)keyStore.getCertificate(alias);
        PrivateKey privateKey = null;
        KeyStore tmpKs = keyStore;
        privateKey = (PrivateKey)tmpKs.getKey(alias, this.passSignature.toCharArray());
        Provider provider = keyStore.getProvider();
        DataToSign dataToSign = createDataToSign(resource);
        FirmaXML firma = new FirmaXML();
        Document docSigned = null;
        Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
        docSigned = (Document)res[0];
        return toByteArray(docSigned);
    }

    private byte[] toByteArray(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        StreamResult result=new StreamResult(bos);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
        byte[] array=bos.toByteArray();
        return array;

    }

    private KeyStore getKeyStore()throws Exception
    {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(this.pathSignature), this.passSignature.toCharArray());
        return ks;
    }

    private String getAlias(KeyStore keyStore) throws Exception
    {
        String alias = null;
        Enumeration nombres = keyStore.aliases();
        while (nombres.hasMoreElements())
        {
           String tmpAlias = (String)nombres.nextElement();
           if (keyStore.isKeyEntry(tmpAlias)) {
             alias = tmpAlias;
           }
        }
        return alias;
    }

    protected DataToSign createDataToSign(ByteArrayOutputStream resource) throws Exception {
        DataToSign datosAFirmar = new DataToSign();
        datosAFirmar.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosAFirmar.setXMLEncoding("UTF-8");
        datosAFirmar.setEnveloped(true);
        datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosAFirmar.setParentSignNode("comprobante");
        Document docToSign = getDocument(resource);
        datosAFirmar.setDocument(docToSign);
        return datosAFirmar;
    }



    protected Document getDocument(ByteArrayOutputStream resource) throws Exception
    {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(resource.toByteArray());
        doc = db.parse(byteArrayInputStream);
        return doc;
    }
}
