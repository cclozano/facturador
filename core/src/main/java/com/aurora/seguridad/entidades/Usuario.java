package com.aurora.seguridad.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
@Table(name = "usuario")
public class Usuario extends BaseEntity {

	@NotNull
	@Size(min = 1, max = 255)
	@Column(unique = true)
	private String email;

	@NotNull
	@Size(min = 4, max = 255)
	private String password;

	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	@NotNull
	@Size(min = 1, max = 255)
	private String rol;

	private boolean locked = false;



	public Usuario() {
		// An empty constructor is needed for all beans
	}

	public Usuario(String email, String name, String password, String rol) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.rol = rol;
	}

}
