package com.example.monitor.management.domain.service.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PhoneNumbersResponseDto {
    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;
}
