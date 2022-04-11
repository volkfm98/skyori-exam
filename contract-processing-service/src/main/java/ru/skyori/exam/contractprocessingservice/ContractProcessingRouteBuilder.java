package ru.skyori.exam.contractprocessingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import ru.skyori.exam.CreateNewContract;

@Component
@RequiredArgsConstructor
public class ContractProcessingRouteBuilder extends RouteBuilder {
    @NonNull
    private Processor saveContractProcessor;

    @Override
    public void configure() throws Exception {
        from("contract:default?queue=contract.create")
                .unmarshal().json(JsonLibrary.Jackson, CreateNewContract.class)
                .process(saveContractProcessor)
                .marshal().json(JsonLibrary.Jackson)
                .removeHeader("CamelRabbitmqRoutingKey")
                .to("contract:default?queue=contract.event&routingKey=contract.event");
    }
}
