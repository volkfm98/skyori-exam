package ru.skyori.exam.contractprocessingservice;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@Getter
@Setter
@RequiredArgsConstructor
public class Contract {
    @Id
    private UUID id;

    private LocalDate dateStart;
    private LocalDate dateEnd;

    private LocalDateTime dateSend;

    @Column(columnDefinition = "timestamp default now()", nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreate;

    // Indexed
    private String contractNumber;

    private String contractName;
    private String clientApi;

    // I really tried hard to avoid another dependency, but it looks like the most popular way to insert jsonb
    @Type(type = "jsonb")
    @Column(columnDefinition = "JSONB")
    private String contractualParties;
}
