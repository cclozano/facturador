package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarjetaCreditoRepositorio extends JpaRepository<TarjetaCredito,Long>{

    List<TarjetaCredito> findByNombreLike(String nombre);
}
