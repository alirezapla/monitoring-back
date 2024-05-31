package com.example.monitor.management.domain.model.security;


import com.example.monitor.management.domain.model.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_credentials")
public class User {

    @Id
    @Size(min = 36, max = 36)
    protected String id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_credential_id"))
    private Set<UserAuthority> userAuthorities = new HashSet<>();

    public User() {
    }

    public User(String id, String username, String password, Set<UserAuthority> userAuthorities) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.userAuthorities = userAuthorities;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    public void updateUser(String username, Set<UserAuthority> userAuthorities) {
        this.username = username;
        this.userAuthorities = userAuthorities;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getId() {
        return this.id;
    }
}
