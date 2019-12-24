package com.aurora.pos.entidades;

public class Rol {
	public static final String ADMIN = "admin";
	public static final String USUARIO = "usuario";
	public static final String CAJERO = "cajero";

	private Rol() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { USUARIO, CAJERO, ADMIN };
	}

}
