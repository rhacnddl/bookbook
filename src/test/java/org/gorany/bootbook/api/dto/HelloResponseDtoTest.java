package org.gorany.bootbook.api.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloResponseDtoTest {

    @Test
    @DisplayName("Hello Dto Test")
    void hello_dto_test() throws Exception {
        //given
        String name = "test";
        int amount = 10;
        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);
        //then
        assertThat(dto.getAmount()).isEqualTo(amount);
        assertThat(dto.getName()).isEqualTo(name);
    }
}