package com.plantier.pedidos.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.plantier.pedidos.domain.Cliente;
import com.plantier.pedidos.dto.ClienteDTO;
import com.plantier.pedidos.repositories.ClienteRepository;
import com.plantier.pedidos.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		// pega o map das variáveis de URI que estão na requisição
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer idUrl = Integer.parseInt(map.get("id"));
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(idUrl)) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		/*
		 * Percorre a lista de FieldMessage e para cada obj da lista adiciona um erro correspondente ao framework.
		 * Transporta os errors personalizados para a lista de erros do framework, que são tratadas e mostradas 
		 * nas respostas no ExceptionHandler
		 */
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		// se a lista de erros estiver vazia, significa que não teve nenhum erro retorna true.
		return list.isEmpty();
		
	}
}