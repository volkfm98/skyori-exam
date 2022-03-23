package ru.skyori.exam.contractservice;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;
import ru.skyori.exam.ContractualParty;
import ru.skyori.exam.CreateNewContract;

import java.util.List;
import java.util.UUID;

@Mapper
public interface NewContractMapper {
    NewContractMapper INSTANCE = Mappers.getMapper(NewContractMapper.class);

    CreateNewContract newContractRequestToNewContract(CreateNewContractRequest newContractRequest);

    ContractualParty requestContractualPartyToContractualPartyDto(
            CreateNewContractRequest.ContractualParties.ContractualParty contractualParty);

    List<ContractualParty> requestContractualPartiesToContractualPartyDtos(
            List<CreateNewContractRequest.ContractualParties.ContractualParty> contractualParties);

    default UUID map(String uuid) {
        return UUID.fromString(uuid);
    }

    default List<ContractualParty> map(CreateNewContractRequest.ContractualParties contractualParties) {
        return requestContractualPartiesToContractualPartyDtos(contractualParties.getContractualParty());
    }
}
