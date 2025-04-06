package com.project.command.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Converter
public class ListToArrayConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(";", attribute);
    }

    /**
     * Преобразует строку с данными, разделенными символом ";", в список строк.
     * Создается объект Optional. Если dbData равен null, то Optional будет пустым (Optional.empty()).
     * StringUtils.isNotBlank() проверяет, что строка не является: null, пустой строкой (""), не состоит из пробелов (" ").
     * Результат — список строк, где каждая строка представляет собой одну из частей исходной строки, разделенных ";".
     * @param String dbData
     */
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .filter(StringUtils::isNotBlank)
                .map(it -> List.of(it.split(";")))
                .orElseGet(ArrayList::new);
    }
}
