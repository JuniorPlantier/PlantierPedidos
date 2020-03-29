package com.plantier.pedidos.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plantier.pedidos.domain.Cliente;
import com.plantier.pedidos.repositories.ClienteRepository;
import com.plantier.pedidos.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		// https://unicode-table.com/
		
		if(opt == 0) { // gera um dígito
			return (char) (random.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiúscula
			return (char) (random.nextInt(26) + 65);
		} else { // gera letra minúscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
