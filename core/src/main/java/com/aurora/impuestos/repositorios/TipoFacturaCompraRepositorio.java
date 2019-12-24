package com.aurora.impuestos.repositorios;

import com.aurora.impuestos.entidades.TipoFacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoFacturaCompraRepositorio extends JpaRepository<TipoFacturaCompra,Long> {

    List<TipoFacturaCompra> findAllByNombreLike(String nombre);
}
