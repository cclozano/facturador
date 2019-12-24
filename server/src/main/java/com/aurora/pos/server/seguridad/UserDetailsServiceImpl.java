package com.aurora.pos.server.seguridad;

import com.aurora.seguridad.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserServiceImp userService;

	@Autowired
	public UserDetailsServiceImpl(UserServiceImp userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userService.findByEmail(username);
		//user = new Usuario();
		//user.setName("cmendoza");
		//user.setPassword("123");
		//user.setRole("admin");
		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRol())));
		}
	}
}