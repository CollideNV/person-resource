package be.collide.service;

import be.collide.domain.Person;
import be.collide.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class PersonRepository {
    private DynamoDbTable<Person> personTable;

    @Inject
    DynamoDbEnhancedClient client;

    @PostConstruct
    void postConstruct() {
        personTable = client.table("Person", TableSchema.fromBean(Person.class));

        try {
            personTable.createTable(builder -> builder
                    .provisionedThroughput(b -> b
                            .readCapacityUnits(10L)
                            .writeCapacityUnits(10L)
                            .build())
            );
        } catch (ResourceInUseException e) {
            log.debug("Dit not recreate Quiz table as it already exists");
        }
    }

    public Person create(Person person) {
        personTable.putItem(person);
        return person;
    }

    public Person get(UUID id) {
        return Optional.ofNullable(personTable.getItem(Key.builder().partitionValue(id.toString()).build()))
                .orElseThrow(() -> new ResourceNotFoundException("Person with id %s not found!".formatted(id)));
    }


}
