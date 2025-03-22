package com.project.command.converter;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.jdbc4.Jdbc4Array;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class ListToArrayConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(";", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .filter(StringUtils::isNotBlank)
                .map(it -> List.of(it.split(";")))
                .orElseGet(ArrayList::new);
    }
}