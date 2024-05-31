package com.example.monitor.management.domain.service.user;


import com.example.monitor.management.common.AppLogEvent;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.InvalidPasswordException;
import com.example.monitor.management.common.exceptions.UserCredentialNotFoundException;
import com.example.monitor.management.domain.model.security.User;
import com.example.monitor.management.domain.model.security.UserRepository;
import com.example.monitor.management.domain.service.user.dto.LoginDTO;
import com.example.monitor.management.domain.service.user.dto.LoginSuccessDTO;
import com.example.monitor.management.infrastructure.utils.JwtUtil;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService{
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MyLogger logger;


    public AuthenticationService(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, MyLogger logger) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    public LoginSuccessDTO login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        logger.doLog(LogLevel.INFO,AppLogEvent.LOGIN_SERVICE_STARTED, username);

        String password = loginDTO.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserCredentialNotFoundException(
                        ExceptionMessages.USER_CREDENTIAL_NOT_FOUND.getTitle()));
        checkPassword(user, password);
        return LoginSuccessDTO.mapFromUserCredentials(user, jwtUtil.generateToken(user));
    }


    private void checkPassword(User user, String password) {
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new InvalidPasswordException(ExceptionMessages.PASSWORD_IS_NOT_VALID.getTitle());
        }
    }
}

