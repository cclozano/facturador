package com.aurora.pos.sri.recepcion;

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
import java.util.Vector;


public class respuestaSolicitud_comprobantes extends Vector< comprobante> implements KvmSerializable
{
    
    public respuestaSolicitud_comprobantes(){}
    
    public respuestaSolicitud_comprobantes(Object inObj,ExtendedSoapSerializationEnvelope __envelope)
    {
        if (inObj == null)
            return;
        SoapObject soapObject=(SoapObject)inObj;
        int size = soapObject.getPropertyCount();
        for (int i0=0;i0< size;i0++)
        {
            Object obj = soapObject.getProperty(i0);
            if (obj!=null && obj instanceof AttributeContainer)
            {
                AttributeContainer j =(AttributeContainer) soapObject.getProperty(i0);
                comprobante j1= (comprobante)__envelope.get(j,comprobante.class,false);
                add(j1);
            }
        }
}

    @Override
    public Object getProperty(int arg0) {
        return this.get(arg0)!=null?this.get(arg0):SoapPrimitive.NullNilElement;
    }

    @Override
    public int getPropertyCount() {
        return this.size();
    }

    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        info.name = "comprobante";
        info.type = comprobante.class;
    	info.namespace= "";
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    
}