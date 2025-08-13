package com.github.geo_gkez.match_service.converter;

import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SportEnumConverter implements AttributeConverter<SportEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SportEnum sportEnum) {
        if (sportEnum == null) {
            return null;
        }

        return sportEnum.getCode();
    }

    @Override
    public SportEnum convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return SportEnum.fromValue(code);
    }
}
