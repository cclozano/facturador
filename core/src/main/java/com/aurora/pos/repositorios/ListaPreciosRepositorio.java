package com.aurora.pos.repositorios;

import com.aurora.pos.entidades.ListaPrecios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListaPreciosRepositorio extends JpaRepository<ListaPrecios,Long> {

    @Query("select lp from ListaPrecios lp where ( lower(lp.nombreCorto) like ?1 or ?1 ='%%')")
    List<ListaPrecios> filter(String filro);

   // List<ListaPrecios> findByContribuyente(Contribuyente contribuyente);
}
