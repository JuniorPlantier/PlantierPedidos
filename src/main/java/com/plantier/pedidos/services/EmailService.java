package com.plantier.pedidos.services;

import org.springframework.mail.SimpleMailMessage;

import com.plantier.pedidos.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
