package com.plantier.pedidos.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.plantier.pedidos.domain.Cliente;
import com.plantier.pedidos.domain.enums.TipoCliente;
import com.plantier.pedidos.dto.ClienteNewDTO;
import com.plantier.pedidos.repositories.ClienteRepository;
import com.plantier.pedidos.resources.exceptions.FieldMessage;
import com.plantier.pedidos.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux == null) {
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