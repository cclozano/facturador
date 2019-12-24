package com.aurora.inventario.repositorios;

import com.aurora.inventario.entidades.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadMedidaRepositorio extends JpaRepository<UnidadMedida,Long>{

    List<UnidadMedida> findByNombreLikeOrCodigoLike(String nombre,String codigo);
}
