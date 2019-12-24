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

public class mensaje extends AttributeContainer implements KvmSerializable
{

    
    public String identificador;
    
    public String mensaje;
    
    public String informacionAdicional;
    
    public String tipo;

    public mensaje ()
    {
    }

    public mensaje (Object paramObj,ExtendedSoapSerializationEnvelope __envelope)
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
                if (info.name.equals("identificador"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.identificador = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.identificador = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("mensaje"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.mensaje = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.mensaje = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("informacionAdicional"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.informacionAdicional = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.informacionAdicional = (String)obj;
                        }
                    }
                    continue;
                }
                if (info.name.equals("tipo"))
                {
                    if(obj!=null)
                    {

                        if (obj.getClass().equals(SoapPrimitive.class))
                        {
                            SoapPrimitive j =(SoapPrimitive) obj;
                            if(j.toString()!=null)
                            {
                                this.tipo = j.toString();
                            }
                        }
                        else if (obj instanceof String){
                            this.tipo = (String)obj;
                        }
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
            return this.identificador!=null?this.identificador:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.mensaje!=null?this.mensaje:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.informacionAdicional!=null?this.informacionAdicional:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==3)
        {
            return this.tipo!=null?this.tipo:SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "identificador";
            info.namespace= "";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "mensaje";
            info.namespace= "";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "informacionAdicional";
            info.namespace= "";
        }
        if(propertyIndex==3)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "tipo";
            info.namespace= "";
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

    
}
