package be.collide.util.converter;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RegisterForReflection
public class LocalDateToStringTypeConverter implements AttributeConverter<LocalDate> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

    @Override
    public AttributeValue transformFrom(LocalDate input) {
        return AttributeValue.fromS(input.format(dateTimeFormatter));
    }

    @Override
    public LocalDate transformTo(AttributeValue input) {
        return LocalDate.parse(input.s(), dateTimeFormatter);
    }

    @Override
    public EnhancedType<LocalDate> type() {
        return EnhancedType.of(LocalDate.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
