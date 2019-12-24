package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpuestoRepositorio extends JpaRepository<Impuesto,Long> {
}
