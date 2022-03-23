package ru.skyori.exam.contractprocessingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContractProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractProcessingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner camelStart(CamelContext ctx, Component contractEventQueueComponent) {
		return (arg) -> {
			// There's warning about that, but I'm not sure what to do
			ctx.addComponent("contract", contractEventQueueComponent);
			ctx.start();
		};
	}
}
