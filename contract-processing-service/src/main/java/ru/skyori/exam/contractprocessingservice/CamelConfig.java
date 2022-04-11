package ru.skyori.exam.contractprocessingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Processor;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
public class CamelConfig {
    @Resource
    private Environment env;

    @Bean
    ConnectionFactory rabbitConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(env.getProperty("spring.rabbitmq.host"));
        connectionFactory.setPort(Integer.parseInt(env.getProperty("spring.rabbitmq.port")));
        connectionFactory.setUsername(env.getProperty("spring.rabbitmq.username"));
        connectionFactory.setPassword(env.getProperty("spring.rabbitmq.password"));

        return connectionFactory;
    }

    @Bean
    Component contractEventQueueComponent(ConnectionFactory rabbitConnectionFactory, CamelContext ctx) {
        RabbitMQComponent component = new RabbitMQComponent();
        component.setConnectionFactory(rabbitConnectionFactory);
        component.setDeclare(true);

        ctx.addComponent("contract", component);

        return component;
    }

    @Bean
    Processor saveContractProcessor(ContractRepository repo, ContractMapper contractMapper) {
        return new SaveContractProcessor(repo, contractMapper);
    }

    @Bean
    ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }
}
