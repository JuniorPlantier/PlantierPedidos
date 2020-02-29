package com.plantier.pedidos.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.plantier.pedidos.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg); // Mesagem com o texto plano.
	
	void sendOrderConfirmationHtmlEmail(Pedido obj); 
	void sendHtmlEmail(MimeMessage msg); // Mensagem com HTML.
}
