package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void givenUser_whenConvertToDto_thenReturnUserDto() {
        User user = new User(1L, "investor1", "John", "Doe", new BigDecimal("50000"));
        UserDto userDto = userMapper.convertToDto(user);
        assertEquals("investor1", userDto.getUsername());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals(new BigDecimal("50000.00"), userDto.getWalletBalance());
    }
}