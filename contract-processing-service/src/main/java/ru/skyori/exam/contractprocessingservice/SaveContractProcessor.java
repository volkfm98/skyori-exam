package ru.skyori.exam.contractprocessingservice;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.skyori.exam.ContractStatus;
import ru.skyori.exam.CreateNewContract;

@RequiredArgsConstructor
public class SaveContractProcessor implements Processor {
    @NonNull
    private final ContractRepository contractRepository;
    @NonNull
    private final ContractMapper contractMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        CreateNewContract newContract = exchange.getIn().getBody(CreateNewContract.class);
        Contract contractEntity = contractMapper.CreateNewContractToContract(newContract);
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
    }
}
