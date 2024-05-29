package com.example.monitor.management.infrastructure.config;

import com.example.monitor.management.domain.model.security.UserAuthorityType;
import com.example.monitor.management.domain.service.user.CustomUserDetailsService;
import com.example.monitor.management.infrastructure.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .mvcMatchers(HttpMethod.GET, "/document")
                .hasAnyAuthority(UserAuthorityType.AUTHORITY_ADMIN.getTitle())
                .mvcMatchers(HttpMethod.PUT, "/document/{id}").hasAnyAuthority(UserAuthorityType.AUTHORITY_ADMIN.getTitle())
                .mvcMatchers(HttpMethod.DELETE, "/document/{id}").hasAnyAuthority(UserAuthorityType.AUTHORITY_ADMIN.getTitle())
                .mvcMatchers(HttpMethod.POST, "/document")
                .hasAnyAuthority(UserAuthorityType.AUTHORITY_ADMIN.getTitle())

                .anyRequest()
                .hasAuthority(UserAuthorityType.AUTHORITY_ADMIN.getTitle())
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

