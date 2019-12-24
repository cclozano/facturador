package com.aurora.pos.servicios.imp;

import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.repositorios.EmisorRepositorio;
import com.aurora.pos.servicios.ServicioEmisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicioEmisorImp implements ServicioEmisor {

    @Autowired
    private EmisorRepositorio emisorRepositorio;

    @Override
    public Emisor getEmisor() {
        List<Emisor> emisors = this.emisorRepositorio.findAll();
        if(emisors.size()== 0)
            return null;
        return emisors.get(0);
    }
}
