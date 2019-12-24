package com.aurora.inventario.repositorios;

import com.aurora.inventario.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto,Long>{
}
