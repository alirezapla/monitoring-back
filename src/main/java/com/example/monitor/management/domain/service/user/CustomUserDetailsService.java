package com.example.monitor.management.domain.service.user;


import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.UserCredentialNotFoundException;
import com.example.monitor.management.domain.model.security.CustomUserDetails;
import com.example.monitor.management.domain.model.security.User;
import com.example.monitor.management.domain.model.security.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserCredentialNotFoundException(ExceptionMessages.USER_CREDENTIAL_NOT_FOUND.getTitle()));
        return new CustomUserDetails(user);
    }
}
