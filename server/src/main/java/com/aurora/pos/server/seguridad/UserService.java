package com.aurora.pos.server.seguridad;

import com.aurora.seguridad.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {
    Usuario findByEmail(String email);
    Page<Usuario> findAnyMatching(Optional<String> filter, Pageable pageable);
    long countAnyMatching(Optional<String> filter);
    String encodePassword(String value);
    @Transactional
    Usuario save(Usuario entity);
    @Transactional
    void delete(long userId);
    Usuario getCurrentUser();
}
