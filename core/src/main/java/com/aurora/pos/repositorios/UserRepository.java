package com.aurora.pos.repositorios;

import com.aurora.seguridad.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	Page<Usuario> findByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrRolLikeIgnoreCase(String emailLike, String nameLike,
                                                                                    String rolLike, Pageable pageable);

	long countByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(String emailLike, String nameLike);


	List<Usuario> findByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(String emailLike, String nameLike);
}
