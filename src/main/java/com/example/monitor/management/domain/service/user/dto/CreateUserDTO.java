package com.example.monitor.management.domain.service.user.dto;



import com.example.monitor.management.domain.model.security.User;
import com.example.monitor.management.domain.model.security.UserAuthority;
import com.example.monitor.management.domain.model.security.UserAuthorityType;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateUserDTO {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Set<String> authorities;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String username, String password, String firstname, String lastname, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public User toEntity() {
        return new User(
                UUID.randomUUID().toString(),
                this.username,
                this.password,
                this.authorities
                        .stream()
                        .map(UserAuthorityType::getByUserAuthorityTitle)
                        .map(UserAuthority::new)
                        .collect(Collectors.toSet())
        );
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
