package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;
import com.aquariux.assessment.cryptoapp.model.Crypto;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CryptoMapper {
    @Mapping(target = "cryptoName", source = "cryptoName", qualifiedByName = "capitalize")
    @Mapping(target = "cryptoTicker", source = "cryptoTicker", qualifiedByName = "toUpperCase")
    @Mapping(target = "bidPrice", source = "bidPrice", qualifiedByName = "trimTrailingZeros")
    @Mapping(target = "askPrice", source = "askPrice", qualifiedByName = "trimTrailingZeros")
    CryptoDto convertToDto(Crypto crypto);

    @Mapping(target = "cryptoName", source = "cryptoName", qualifiedByName = "capitalize")
    @Mapping(target = "cryptoTicker", source = "cryptoTicker", qualifiedByName = "toUpperCase")
    @Mapping(target = "bidPrice", source = "bidPrice", qualifiedByName = "trimTrailingZeros")
    @Mapping(target = "askPrice", source = "askPrice", qualifiedByName = "trimTrailingZeros")
    Crypto convertToEntity(CryptoDto cryptoDto);

    @Mapping(target = "cryptoName", source = "cryptoName", qualifiedByName = "capitalize")
    @Mapping(target = "cryptoTicker", source = "cryptoTicker", qualifiedByName = "toUpperCase")
    @Mapping(target = "bidPrice", source = "bidPrice", qualifiedByName = "trimTrailingZeros")
    @Mapping(target = "askPrice", source = "askPrice", qualifiedByName = "trimTrailingZeros")
    Crypto updateEntityWithDto(CryptoDto cryptoDto, @MappingTarget Crypto crypto);

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