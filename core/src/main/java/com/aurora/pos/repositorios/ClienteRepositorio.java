package com.aurora.pos.repositorios;

import com.aurora.framework.data.BaseRepository;
import com.aurora.pos.entidades.Cliente;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by max on 09/08/17.
 */
public interface ClienteRepositorio extends JpaRepository<Cliente,Long>,BaseRepository<Cliente> {

   // List<Cliente> findByNombreLike(String filtro);

   // List<Cliente> findByIdentificacionLike(String identificiacion);

 //   List<Cliente> findByNombreLikeIgnoreCaseOrIdentificacionLikeIgnoreCase(String filtroNombre,String filtroIdentificacion);

    @Query("select c from Cliente c where lower(c.nombre)  like ?1 or c.identificacion like ?1 or ?1 = '%%'")
    List<Cliente> filer(String filter);

    @Query("select c from Cliente c where lower(c.nombre)  like ?1 or c.identificacion like ?1 or ?1 = '%%'")
    List<Cliente> filer(String filter, Pageable pageable);

    @Query("select count(c) from Cliente c where lower(c.nombre)  like ?1 or c.identificacion like ?1 or ?1 = '%%'")
    int countFilter(String filter);
}
