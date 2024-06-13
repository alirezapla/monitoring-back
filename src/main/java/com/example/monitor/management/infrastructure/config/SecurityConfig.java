package com.example.monitor.management.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig() {
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password("$2a$12$svqpC4mrnl0u1ET6u9A9feNlvsXip7mfv75Jg/sL.EC1zCq5FLWGe") //pass
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password("$2a$12$aoPYjva/xq73w0hSMZwg5OMUWLt3ylsfq1OnKXBXxgAI.eO1mXX.e") //secret
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .httpBasic(withDefaults())
                .csrf()
                .disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/documents").permitAll()
                .mvcMatchers(HttpMethod.GET, "/documents/{id}").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/documents/{id}").hasAnyAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/documents/{id}").hasAnyAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/documents").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .hasAuthority("ROLE_ADMIN")
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

