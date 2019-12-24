package com.aurora.framework.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by max on 22/08/17.
 */
public interface BaseRepository<E extends BaseEntity>  {

    List<E> findByActivo(Boolean estado);
}
