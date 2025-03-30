package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.model.User;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "capitalize")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "capitalize")
    @Mapping(target = "walletBalance", source = "walletBalance", qualifiedByName = "trimDecimals")
    UserDto convertToDto(User user);

    @Named("toUpperCase")
    default String toUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    @Named("capitalize")
    default String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return Arrays.stream(value.split("\\s+")).map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining(" "));
    }

    @Named("trimDecimals")
    default BigDecimal trimDecimals(BigDecimal value) {
        if (value != null) {
            return value.setScale(2, RoundingMode.HALF_UP);
        }
        return null;
    }
}