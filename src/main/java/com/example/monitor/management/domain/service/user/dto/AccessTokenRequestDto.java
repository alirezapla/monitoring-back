package com.example.monitor.management.domain.service.user.dto;

import lombok.Getter;

@Getter
public class AccessTokenRequestDto {
    private String code;
    private String client_id;
    private String client_secret;
    private String grant_type;

    public AccessTokenRequestDto(String code, String client_id, String client_secret) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = "authorization_code";
    }

//    @JsonProperty("code")
//    public String getCode() {
//        return code;
//    }
//    @JsonProperty("client_secret")
//    public String getClientSecret() {
//        return clientSecret;
//    }
//    @JsonProperty("client_type")
//    public String getGrantType() {
//        return grantType;
//    }
//
//    @JsonProperty("client_id")
//    public String getClientId () {
//        return clientId;
//    }


}
