package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.List;
import java.util.Set;
import com.example.monitor.management.domain.model.Computation;
@Converter(autoApply=true)
public class JsonConvertor implements AttributeConverter<Set<Computation>,String> {
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<Computation> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Computation> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData,Set.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
