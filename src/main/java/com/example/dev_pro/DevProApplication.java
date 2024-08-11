package com.example.dev_pro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition 
public class DevProApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevProApplication.class, args);
	}

}
