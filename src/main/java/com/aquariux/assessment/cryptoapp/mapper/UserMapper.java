package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.model.User;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "capitalize")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "capitalize")
    @Mapping(target = "walletBalance", qualifiedByName = "trimTrailingZeros")
    UserDto convertToDto(User user);

    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "capitalize")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "capitalize")
    @Mapping(target = "walletBalance", qualifiedByName = "trimTrailingZeros")
    User convertToEntity(UserDto userDto);

    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "capitalize")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "capitalize")
    @Mapping(target = "walletBalance", qualifiedByName = "trimTrailingZeros")
    User updateEntityWithDto(UserDto userDto, @MappingTarget User user);

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

    @Named("trimTrailingZeros")
    default String trimTrailingZeros(String value) {
        List<String> parts = Arrays.asList(value.split("\\."));
        if (parts.size() >= 2) {
            List<String> parts2 = new ArrayList<>(List.of(parts.get(1).split("0")));
            return parts.get(0).concat(".").concat(String.join("0", parts2));
        }
        return value;
    }
}