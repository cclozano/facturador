package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstablecimientoRepositorio extends JpaRepository<Establecimiento,Long>{

}
