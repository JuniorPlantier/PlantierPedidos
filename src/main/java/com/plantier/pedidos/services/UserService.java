package com.plantier.pedidos.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.plantier.pedidos.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			// retorna o usuário que estiver logado no sistema.
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) { 
			return null;
		}
	}
	
}