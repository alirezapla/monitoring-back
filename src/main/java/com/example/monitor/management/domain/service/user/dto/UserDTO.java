package com.example.monitor.management.domain.service.user.dto;


import com.example.monitor.management.domain.model.security.User;
import com.example.monitor.management.domain.model.security.UserAuthority;
import com.example.monitor.management.domain.model.security.UserAuthorityType;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private String id;
    private String username;
    private Set<String> authorities;

    public UserDTO() {
    }

    public UserDTO(String id, String username, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getUserAuthorities()
                        .stream()
                        .map(UserAuthority::getAuthorityType)
                        .map(UserAuthorityType::getTitle)
                        .collect(Collectors.toSet())
        );
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}
