package com.aurora.impuestos.repositorios;

import com.aurora.impuestos.entidades.RetencionImpuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RetencionImpuestoRepositorio { /* extends JpaRepository<RetencionImpuesto,Long>{
{

    @Query("select r from RetencionImpuesto r where r.factura.id = ?1")
    RetencionImpuesto obtenerPorFactura(Long facturaId);

    List<RetencionImpuesto> getByFechaBetween(Date fechaInicio,Date fechaFin);*/
}
