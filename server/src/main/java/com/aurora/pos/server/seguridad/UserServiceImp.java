package com.aurora.pos.server.seguridad;


import com.aurora.seguridad.entidades.Usuario;
import com.aurora.pos.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

	private static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "Usuario has been locked and cannot be modified or deleted";
	private  PasswordEncoder passwordEncoder;
	private  UserRepository userRepository;


	@Autowired
	public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	public Usuario findByEmail(String email) {
		return getRepository().findByEmail(email);
	}


	@Override
	public Page<Usuario> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrRolLikeIgnoreCase(repositoryFilter,
					repositoryFilter, repositoryFilter, pageable);
		} else {
			return getRepository().findAll(pageable);
		}
	}


	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByEmailLikeIgnoreCaseOrNameLikeIgnoreCase(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}


	protected UserRepository getRepository() {
		return userRepository;
	}

	@Override
	public String encodePassword(String value) {
		return passwordEncoder.encode(value);
	}


	@Override
	@Transactional
	public Usuario save(Usuario entity) {
		throwIfUserLocked(entity.getId());
		return userRepository.save(entity);
	}


	@Override
	@Transactional
	public void delete(long userId) {
		throwIfUserLocked(userId);
		userRepository.delete(userId);
	}

	private void throwIfUserLocked(Long userId) {
		if (userId == null) {
			return;
		}

		Usuario dbUser = getRepository().findOne(userId);
		if (dbUser.isLocked()) {
			throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
		}
	}


	@Override
	public Usuario getCurrentUser()
	{
		Usuario user = findByEmail(SecurityUtils.getUsername());
		return user;
	}


}
