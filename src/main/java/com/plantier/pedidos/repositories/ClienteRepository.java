package com.plantier.pedidos.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.plantier.pedidos.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	// Recurso do springdata que usa um padrão de nomes. 
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
}
