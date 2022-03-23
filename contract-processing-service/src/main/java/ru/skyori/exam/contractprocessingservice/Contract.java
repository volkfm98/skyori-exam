package ru.skyori.exam.contractprocessingservice;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(indexes = @Index(columnList = "contractNumber", unique = true))
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
public class Contract {
    @Id
    private UUID id;

    private LocalDate dateStart;
    private LocalDate dateEnd;

    private LocalDateTime dateSend;

    @CreatedDate
    private LocalDateTime dateCreate;

    // Indexed
    private String contractNumber;

    private String contractName;
    private String clientApi;

    // I really tried hard to avoid another dependency, but it looks like the most popular way to insert jsonb
    @Type(type = "jsonb")
    @Column(columnDefinition = "JSONB")
    private String contractualParties;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getClientApi() {
        return clientApi;
    }

    public void setClientApi(String clientApi) {
        this.clientApi = clientApi;
    }

    public String getContractualParties() {
        return contractualParties;
    }

    public void setContractualParties(String contractualParties) {
        this.contractualParties = contractualParties;
    }
}
