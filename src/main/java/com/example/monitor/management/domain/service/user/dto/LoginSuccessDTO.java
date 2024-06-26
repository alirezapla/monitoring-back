package com.example.monitor.management.domain.service.user.dto;


import com.example.monitor.management.domain.model.security.User;

import java.util.Set;
import java.util.stream.Collectors;

public class LoginSuccessDTO {
    public String jwt;
    private Set<String> userAccountAuthorities;

    public LoginSuccessDTO() {
    }

    public LoginSuccessDTO(String jwt, Set<String> userAccountAuthorities) {
        this.jwt = jwt;
        this.userAccountAuthorities = userAccountAuthorities;
    }

    public static LoginSuccessDTO mapFromUserCredentials(User user, String generateToken) {
        return new LoginSuccessDTO(
                generateToken,
                user.getUserAuthorities()
                        .stream()
                        .map(us -> us.getAuthorityType().getTitle())
                        .collect(Collectors.toSet())
        );
    }
}
