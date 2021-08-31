package com.ThePirates.Backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class DetailedInfoOptionsConverter implements AttributeConverter<DetailedInfoOptions, String>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(DetailedInfoOptions detailedInfoOptionsoptions) {
        try {
            return objectMapper.writeValueAsString(detailedInfoOptionsoptions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    } @Override
    public DetailedInfoOptions convertToEntityAttribute(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, DetailedInfoOptions.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


