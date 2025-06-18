package com.demo.shiftplanner.shift_planner.service;

import com.demo.shiftplanner.shift_planner.dto.CreateUserRequest;
import com.demo.shiftplanner.shift_planner.dto.UserResponseDTO;
import com.demo.shiftplanner.shift_planner.exceptions.BusinessException;
import com.demo.shiftplanner.shift_planner.model.Role;
import com.demo.shiftplanner.shift_planner.model.User;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createEmployee(CreateUserRequest req) {
        logger.debug("createEmployee: username={}", req.getUsername());

        if (req.getUsername() == null || req.getUsername().isBlank()) {
            throw new BusinessException("Username cannot be blank");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new BusinessException("Password cannot be blank");
        }

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new BusinessException("Username already exists");
        }

        String hash = passwordEncoder.encode(req.getPassword());
        User user = new User(req.getUsername(), hash, Role.EMPLOYEE);
        userRepository.save(user);

        logger.debug("User created: id={}", user.getId());
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole().name());
    }

    public List<UserResponseDTO> listEmployees() {
        logger.debug("listEmployees");

        return userRepository.findAllByRole(Role.EMPLOYEE)
                .stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername(), u.getRole().name()))
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        logger.debug("findById: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found!"));
    }
}
