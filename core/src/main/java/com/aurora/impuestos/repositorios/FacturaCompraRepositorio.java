package com.aurora.impuestos.repositorios;

import com.aurora.impuestos.entidades.FacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FacturaCompraRepositorio extends JpaRepository<FacturaCompra,Long>{

    @Query("select fc from FacturaCompra fc where (lower(fc.nombre) like ?1 or " +
            "lower(fc.ruc) like ?1 or lower(fc.numero) like ?1 or ?1='%%' )  and fc.fecha between ?2 and ?3 order by fc.fecha desc ")
    List<FacturaCompra> filter(String filtro,Date fechaIncio, Date fechaFin);


}
