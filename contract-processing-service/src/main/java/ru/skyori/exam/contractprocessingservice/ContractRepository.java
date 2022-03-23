package ru.skyori.exam.contractprocessingservice;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContractRepository extends CrudRepository<Contract, UUID> {
    boolean existsByContractNumber(String contractNumber);
}
