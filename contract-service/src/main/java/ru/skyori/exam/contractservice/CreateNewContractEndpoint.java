package ru.skyori.exam.contractservice;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;
import ru.esb.xmlns.ext.contractservice.CreateNewContractResponse;
import ru.skyori.exam.CreateNewContract;

@Endpoint
public class CreateNewContractEndpoint {
    private static final String NAMESPACE_URI = "http://xmlns.esb.ru/ext/ContractService/";
    private AmqpTemplate amqpTemplate;

    protected CreateNewContractEndpoint(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateNewContractRequest")
    @ResponsePayload
    public CreateNewContractResponse handleCreateNewContractRequest(@RequestPayload CreateNewContractRequest request) {
        CreateNewContract newContract = NewContractMapper.INSTANCE.newContractRequestToNewContract(request);
        CreateNewContractResponse response = new CreateNewContractResponse();

        try {
            amqpTemplate.convertAndSend("contract.create", newContract);
            response.setStatus("RequestIsQueued");
        } catch (Exception e) {
            response.setStatus("Error");
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
