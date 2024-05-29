package com.example.monitor.management.domain.model.security;



import com.example.monitor.management.domain.model.BaseModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_credentials")
public class User extends BaseModel<User> {


    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_credential_id"))
    private Set<UserAuthority> userAuthorities = new HashSet<>();

    public User() {
    }

    public User(String id,String username, String password, Set<UserAuthority> userAuthorities) {
        super(id);
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

    public void updateUser( String username, Set<UserAuthority> userAuthorities) {
        this.username = username;
        this.userAuthorities = userAuthorities;
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
