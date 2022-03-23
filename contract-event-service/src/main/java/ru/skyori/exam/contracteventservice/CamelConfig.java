package ru.skyori.exam.contracteventservice;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import org.apache.camel.impl.DefaultCamelContext;
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
    Component contractEventQueueComponent(ConnectionFactory rabbitConnectionFactory) {
        RabbitMQComponent component = new RabbitMQComponent();
        component.setConnectionFactory(rabbitConnectionFactory);
        component.setDeclare(true);

        return component;
    }

    @Bean
    RouteBuilder contractEventToHttpRouteBuilder() {
        final String HTTP_HOST = env.getProperty("http.host");
        final String HTTP_PORT = env.getProperty("http.port");

        return new RouteBuilder() {
            @Override
            public void configure() {
                from("contract:default?queue=contract.event")
                        .to("http:" + HTTP_HOST + ":" + HTTP_PORT + "/status");
            }
        };
    }

    @Bean
    CamelContext camelContext(Component contractEventQueueComponent) throws Exception {
        CamelContext ctx = new DefaultCamelContext();

        ctx.addComponent("contract", contractEventQueueComponent);

        return ctx;
    }
}
