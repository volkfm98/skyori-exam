package ru.skyori.exam.contractprocessingservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import ru.skyori.exam.ContractualParty;
import ru.skyori.exam.CreateNewContract;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    Contract CreateNewContractToContract(CreateNewContract newContract);

    default String map(List<ContractualParty> parties) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(parties);
    }
}
