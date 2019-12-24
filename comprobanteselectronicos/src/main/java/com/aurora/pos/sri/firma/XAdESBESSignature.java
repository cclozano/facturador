package com.aurora.pos.sri.firma;

import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.w3c.dom.Document;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by max on 18/05/17.
 */
public class XAdESBESSignature
        extends GenericXMLSignature
{
    //nombre archivo salida
    private static String nameFile;
    //directorio archivo salida
    private static String pathFile;
    private String fileToSign;

    public XAdESBESSignature(String fileToSign)
    {
        this.fileToSign = fileToSign;
    }

    public static void firmar(String xmlPath, String pathSignature, String passSignature, String pathOut, String nameFileOut)
            throws CertificateException, IOException
    {
        XAdESBESSignature signature = new XAdESBESSignature(xmlPath);
        signature.setPassSignature(passSignature);
        signature.setPathSignature(pathSignature);
        pathFile = pathOut;
        nameFile = nameFileOut;

        signature.execute();
    }

    protected DataToSign createDataToSign()
    {
        DataToSign datosAFirmar = new DataToSign();

        datosAFirmar.setXadesFormat(EnumFormatoFirma.XAdES_BES);

        datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosAFirmar.setXMLEncoding("UTF-8");
        datosAFirmar.setEnveloped(true);
        datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosAFirmar.setParentSignNode("comprobante");

        Document docToSign = null;
        try {
            docToSign = getDocument(this.fileToSign);
        } catch (IOException ex) {
            Logger.getLogger(XAdESBESSignature.class.getName()).log(Level.SEVERE, null, ex);
        }
        datosAFirmar.setDocument(docToSign);

        return datosAFirmar;
    }

    protected String getSignatureFileName()
    {
        return nameFile;
    }

    protected String getPathOut()
    {
        return pathFile;
    }
}