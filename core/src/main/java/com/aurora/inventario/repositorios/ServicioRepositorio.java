package com.aurora.inventario.repositorios;

import com.aurora.inventario.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicioRepositorio extends JpaRepository<Servicio,Long> {


    List<Servicio> findByNombreLikeOrCodigoLike(String nombreFilter, String codigoLike);
}
