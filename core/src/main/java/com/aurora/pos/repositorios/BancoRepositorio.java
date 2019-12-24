package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepositorio extends JpaRepository<Banco,Long> {

    List<Banco> findByNombreLike(String nombreFiltro);
}
