package com.nawhew.awsboot.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "lombok";
        int amount = 1000;

        // when
        HelloResponseDto hello = new HelloResponseDto(name, amount);

        // then
        assertThat(hello.getName()).isEqualTo(name);
        assertThat(hello.getAmount()).isEqualTo(amount);
    }
}