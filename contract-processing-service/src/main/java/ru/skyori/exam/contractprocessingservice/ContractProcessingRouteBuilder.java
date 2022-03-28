package ru.skyori.exam.contractprocessingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;
import ru.skyori.exam.ContractStatus;
import ru.skyori.exam.CreateNewContract;

@Component
public class ContractProcessingRouteBuilder extends RouteBuilder {
    private ContractRepository contractRepository;
    private JacksonDataFormat jacksonDataFormat;

    public ContractProcessingRouteBuilder(ContractRepository contractRepository, ObjectMapper objectMapper) {
        this.contractRepository = contractRepository;
        this.jacksonDataFormat = new JacksonDataFormat(objectMapper, CreateNewContract.class);
    }

    @Override
    public void configure() throws Exception {
        from("contract:default?queue=contract.create")
                .unmarshal(jacksonDataFormat)
                .process((exchange -> {
                    CreateNewContract newContract = exchange.getIn().getBody(CreateNewContract.class);
                    Contract contractEntity = ContractMapper.INSTANCE.CreateNewContractToContract(newContract);
                    ContractStatus status = new ContractStatus();

                    if (contractRepository.existsById(contractEntity.getId())) {
                        status.setErrorCode(1);
                        status.setStatus(ContractStatus.Status.ERROR);
                    } else {
                        if (contractRepository.existsByContractNumber(contractEntity.getContractNumber())) {
                            status.setErrorCode(2);
                            status.setStatus(ContractStatus.Status.ERROR);
                        }
                    }

                    if (status.getStatus() != ContractStatus.Status.ERROR) {
                        contractEntity = contractRepository.save(contractEntity);

                        status.setDateCreate(contractEntity.getDateCreate());
                        status.setStatus(ContractStatus.Status.CREATED);
                    }

                    status.setId(contractEntity.getId());

                    exchange.getIn().setBody(status, ContractStatus.class);
                })).marshal(jacksonDataFormat)
                .removeHeader("CamelRabbitmqRoutingKey")
                .to("contract:default?queue=contract.event&routingKey=contract.event");
    }
}
