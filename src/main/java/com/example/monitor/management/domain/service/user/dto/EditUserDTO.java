package com.example.monitor.management.domain.service.user.dto;

import java.util.Set;

public class EditUserDTO {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private Set<String> authorities;

    public EditUserDTO() {
    }

    public EditUserDTO(String id, String username, String firstname, String lastname, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
}
