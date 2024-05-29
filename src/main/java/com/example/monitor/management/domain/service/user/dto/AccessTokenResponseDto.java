package com.example.monitor.management.domain.service.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccessTokenResponseDto {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    private String expires;

    public AccessTokenResponseDto() {

    }

    public AccessTokenResponseDto(String accessToken, String refreshToken, String expires) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expires = expires;
    }
}
