package com.ThePirates.Backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class OptionsConverter implements AttributeConverter<Options, String>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Options options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    } @Override
    public Options convertToEntityAttribute(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Options.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


