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

public class autorizacion extends AttributeContainer implements KvmSerializable
{

    
    public String estado;
    
    public String numeroAutorizacion;
    
    public java.util.Date fechaAutorizacion;
    
    public String ambiente;
    
    public String comprobante;
    
    public autorizacion_mensajes mensajes;

    public autorizacion ()
    {
    }

    public autorizacion (Object paramObj,ExtendedSoapSerializationEnvelope __envelope)
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
                if (info.name.equals("estado"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.estado = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.estado = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("numeroAutorizacion"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.numeroAutorizacion = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.numeroAutorizacion = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("fechaAutorizacion"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.fechaAutorizacion = Helper.ConvertFromWebService(j.toString());
                            }
                        }
                        else if (obj instanceof java.util.Date){
                            this.fechaAutorizacion = (java.util.Date)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("ambiente"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.ambiente = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.ambiente = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("comprobante"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.comprobante = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.comprobante = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("mensajes"))
                {
                    if(obj!=null)
                    {
                        Object j = obj;
                        this.mensajes = new autorizacion_mensajes(j,__envelope);
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
            return this.estado!=null?this.estado:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.numeroAutorizacion!=null?this.numeroAutorizacion:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.fechaAutorizacion!=null?Helper.getDateTimeFormat().format(this.fechaAutorizacion):SoapPrimitive.NullSkip;
        }
        if(propertyIndex==3)
        {
            return this.ambiente!=null?this.ambiente:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==4)
        {
            return this.comprobante!=null?this.comprobante:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==5)
        {
            return this.mensajes!=null?this.mensajes:SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "estado";
            info.namespace= "";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "numeroAutorizacion";
            info.namespace= "";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "fechaAutorizacion";
            info.namespace= "";
        }
        if(propertyIndex==3)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "ambiente";
            info.namespace= "";
        }
        if(propertyIndex==4)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "comprobante";
            info.namespace= "";
        }
        if(propertyIndex==5)
        {
            info.type = PropertyInfo.VECTOR_CLASS;
            info.name = "mensajes";
            info.namespace= "";
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

    
}

