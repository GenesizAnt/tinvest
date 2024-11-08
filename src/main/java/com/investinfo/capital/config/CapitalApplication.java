package com.investinfo.capital.config;

import com.investinfo.capital.telegram.BotProperties;
import com.investinfo.capital.telegram.ValidationPerson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties({BotProperties.class, ValidationPerson.class})
@SpringBootApplication
@ComponentScan(basePackages = {"com.investinfo.capital"})
public class CapitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapitalApplication.class, args);
	}

}
