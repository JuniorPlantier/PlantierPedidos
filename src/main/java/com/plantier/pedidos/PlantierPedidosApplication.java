package com.plantier.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlantierPedidosApplication implements CommandLineRunner {
	
	/*
	@Autowired
	private S3Service s3Service;
	*/
	public static void main(String[] args) {
		SpringApplication.run(PlantierPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Testa Upload do s3Amazon
		// s3Service.uploadFile("C:\\Users\\junior\\Desktop\\Pedidos\\fotos\\curioso.jpg");
	}	

}
