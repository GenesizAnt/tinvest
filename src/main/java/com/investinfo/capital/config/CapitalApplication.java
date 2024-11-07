package com.investinfo.capital.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CapitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapitalApplication.class, args);
	}

}
