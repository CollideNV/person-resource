package be.collide.domain;

import be.collide.util.converter.UUIDToStringConverter;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.UUID;

@Jacksonized
@Getter
@Setter
@Builder(toBuilder = true)
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String firstName;
    private LocalDate birthDate;

    @DynamoDbPartitionKey
    @DynamoDbConvertedBy(UUIDToStringConverter.class)
    public UUID getId() {
        return id;
    }
}
