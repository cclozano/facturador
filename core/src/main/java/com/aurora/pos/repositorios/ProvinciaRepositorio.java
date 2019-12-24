package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by max on 10/08/17.
 */
public interface ProvinciaRepositorio extends JpaRepository<Provincia,Long>{


    List<Provincia> findByNombreLike(String nombre);
}
