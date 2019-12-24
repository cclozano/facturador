package com.aurora.impuestos.repositorios;

import com.aurora.impuestos.entidades.CodigoImpuestoRetencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodigoImpuestoRetencionRepositorio extends JpaRepository<CodigoImpuestoRetencion,Long>{


    List<CodigoImpuestoRetencion>
    findByCodigoImpuestoLikeOrCodigoRetencionLikeOrDescripcionLike(String codigoImpuesto,String codigoRetencion,String descripcion);

    CodigoImpuestoRetencion findByCodigoImpuestoAndCodigoRetencion(String codigoImpuesto,String codigoRetencion);
}
