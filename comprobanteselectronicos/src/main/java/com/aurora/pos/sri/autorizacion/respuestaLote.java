package com.aurora.pos.sri.autorizacion;

//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 5.0.8.5
//
// Created by Quasar Development 
//
//---------------------------------------------------


import org.ksoap2.serialization.*;

import java.util.Hashtable;

public class respuestaLote extends AttributeContainer implements KvmSerializable
{

    
    public String claveAccesoLoteConsultada;
    
    public String numeroComprobantesLote;
    
    public respuestaLote_autorizaciones autorizaciones;

    public respuestaLote ()
    {
    }

    public respuestaLote (Object paramObj,ExtendedSoapSerializationEnvelope __envelope)
    {

	    if (paramObj == null)
            return;
        AttributeContainer inObj=(AttributeContainer)paramObj;


        if(inObj instanceof SoapObject)
        {
            SoapObject soapObject=(SoapObject)inObj;
            int size = soapObject.getPropertyCount();
            for (int i0=0;i0< size;i0++)
            {
                //if you have compilation error here, please use a ksoap2.jar and ExKsoap2.jar from libs folder (in the generated zip file)
                PropertyInfo info=soapObject.getPropertyInfo(i0);
                Object obj = info.getValue();
                if (info.name.equals("claveAccesoLoteConsultada"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.claveAccesoLoteConsultada = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.claveAccesoLoteConsultada = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("numeroComprobantesLote"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.numeroComprobantesLote = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.numeroComprobantesLote = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("autorizaciones"))
                {
                    if(obj!=null)
                    {
                        Object j = obj;
                        this.autorizaciones = new respuestaLote_autorizaciones(j,__envelope);
                    }
                    continue;
                }

            }

        }



    }

    @Override
    public Object getProperty(int propertyIndex) {
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==0)
        {
            return this.claveAccesoLoteConsultada!=null?this.claveAccesoLoteConsultada:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.numeroComprobantesLote!=null?this.numeroComprobantesLote:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.autorizaciones!=null?this.autorizaciones:SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "claveAccesoLoteConsultada";
            info.namespace= "";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "numeroComprobantesLote";
            info.namespace= "";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.VECTOR_CLASS;
            info.name = "autorizaciones";
            info.namespace= "";
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

    
}

