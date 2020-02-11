package com.plantier.pedidos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.plantier.pedidos.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.profiles.active}")
	private String strategy;
	
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"created".equals(strategy)) {
			return false;
		}
		dbService.instantiedTestDatabase();
		return true;
	}
}