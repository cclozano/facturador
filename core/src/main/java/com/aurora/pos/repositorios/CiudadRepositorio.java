package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepositorio extends JpaRepository<Ciudad,Long>{

    List<Ciudad> findByNombreLikeIgnoreCase(String filter);
}
