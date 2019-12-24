package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FacturaRepositorio extends JpaRepository<Factura,Long>{

    @Query("select f from Factura f where (lower(f.cliente.nombre) like ?1 or f.cliente.identificacion like ?1 " +
            "or ?1='%%') and f.fechaEmision between ?2 and ?3  and f.puntoEmision.id = ?4 ")
    List<Factura> filter(String filtro, Date fechaIncio,Date fechaFin,Long idPuntoEmision);


    @Query("select f from Factura f where  f.fechaEmision between ?1 and ?2 and f.estadoEmision.estadoAutorizacion = 'AUTORIZADO'")
    List<Factura> filter(Date fechaIncio,Date fechaFin);

}
