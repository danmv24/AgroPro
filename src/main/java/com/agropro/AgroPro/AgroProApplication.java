package com.agropro.AgroPro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AgroProApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroProApplication.class, args);
	}

}
