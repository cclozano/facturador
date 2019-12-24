package com.aurora.inventario.repositorios;

import com.aurora.inventario.entidades.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepositorio extends JpaRepository<Item,Long> {

    List<Item> findByActivo(Boolean activo);
}
