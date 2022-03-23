package ru.skyori.exam.contracteventservice;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContractEventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractEventServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner camel(CamelContext camelContext) {
		return (arg) -> {
			camelContext.start();
		};
	}
}
