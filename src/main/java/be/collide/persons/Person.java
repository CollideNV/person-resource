package be.collide.persons;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private String linkedInProfile;
    private String emailAddress;
    @Builder.Default
    private List<Employment> employments = new ArrayList<>();

    @DynamoDbPartitionKey
    @DynamoDbConvertedBy(UUIDToStringConverter.class)
    public UUID getId() {
        return id;
    }

    public void addEmployment(UUID id, String company, LocalDate startDate, LocalDate endDate) {
        employments.add(new Employment(id, startDate, endDate, company));
    }


    @Jacksonized
    @Getter
    @Setter
    @Builder(toBuilder = true)
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Employment {
        private UUID id;
        private LocalDate startDate;
        private LocalDate endDate;
        private String company;
    }
}
