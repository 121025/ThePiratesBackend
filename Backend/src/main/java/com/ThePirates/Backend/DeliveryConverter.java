package com.ThePirates.Backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class DeliveryConverter implements AttributeConverter<Delivery, String>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Delivery delivery) {
        try {
            return objectMapper.writeValueAsString(delivery);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    } @Override
    public Delivery convertToEntityAttribute(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Delivery.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


