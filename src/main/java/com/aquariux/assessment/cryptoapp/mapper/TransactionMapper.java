package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "capitalize")
    @Mapping(target = "cryptoTicker", source = "cryptoTicker", qualifiedByName = "toUpperCase")
    TransactionDto convertToDto(Transaction transaction);

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