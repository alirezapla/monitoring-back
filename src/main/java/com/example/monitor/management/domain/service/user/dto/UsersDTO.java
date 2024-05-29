package com.example.monitor.management.domain.service.user.dto;


import com.example.monitor.management.domain.model.security.User;

import java.util.List;
import java.util.stream.Collectors;

public class UsersDTO {

    private final List<UserDTO> users;
    private final Long total;

    public UsersDTO(List<UserDTO> users, Long total) {
        this.users = users;
        this.total = total;
    }

    public static UsersDTO fromEntity(List<User> users, Long totalElements) {
        return new UsersDTO(
                users
                        .stream()
                        .map(UserDTO::fromEntity)
                        .collect(Collectors.toList()),
                totalElements);
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public Long getTotal() {
        return total;
    }
}
