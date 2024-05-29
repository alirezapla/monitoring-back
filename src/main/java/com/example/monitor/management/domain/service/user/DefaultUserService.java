package com.example.monitor.management.domain.service.user;


import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.InvalidPasswordException;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.security.User;
import com.example.monitor.management.domain.model.security.UserAuthorityType;
import com.example.monitor.management.domain.model.security.UserAuthority;
import com.example.monitor.management.domain.model.security.UserRepository;
import com.example.monitor.management.domain.service.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DefaultUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void createUser(CreateUserDTO createUserDTO) {
        createUserDTO.updatePassword(passwordEncoder.encode(createUserDTO.getPassword()));
        userRepository.save(createUserDTO.toEntity());
    }


    public void editUser(EditUserDTO request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle())
                );

        user.updateUser(
                request.getUsername(),
                request.getAuthorities()
                        .stream()
                        .map(UserAuthorityType::getByUserAuthorityTitle)
                        .map(UserAuthority::new)
                        .collect(Collectors.toSet()));

        userRepository.save(user);
    }


    public UserDTO get(String id) {
        return UserDTO.fromEntity(
                userRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()))
        );
    }


    public UsersDTO findAll(int page, int perPage) {
        Page<User> all = userRepository.findAll(PageRequest.of(page - 1, perPage));
        return UsersDTO.fromEntity(all.getContent(), all.getTotalElements());
    }


    public void delete(String id) {
        userRepository.deleteById(id);
    }


    public String changePasswordByAdmin(ChangePasswordByAdminDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle())
                );
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return user.getId();
    }


    public String changePassword(ChangePasswordDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle())
                );

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
            throw new InvalidPasswordException(ExceptionMessages.PASSWORD_IS_NOT_VALID.getTitle());

        if (request.getNewPassword().equals(request.getCurrentPassword()))
            throw new InvalidPasswordException(ExceptionMessages.CURRENT_PASSWORD_IS_EQUAL_TO_NEW_PASSWORD.getTitle());

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return user.getId();
    }
}
