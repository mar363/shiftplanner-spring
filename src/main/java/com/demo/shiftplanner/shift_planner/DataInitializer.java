package com.demo.shiftplanner.shift_planner;

import com.demo.shiftplanner.shift_planner.model.*;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        seedAdmin();
        seedEmployee();
    }

    private void seedAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            String raw = "admin123";
            String hash = passwordEncoder.encode(raw);
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(hash);
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin created!");
        }

    }

    private void seedEmployee() {
        if (userRepository.findByUsername("user1").isEmpty()) {
            User user = new User();
            user.setUsername("user1");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.EMPLOYEE);
            userRepository.save(user);
            System.out.println("User created!");
        }

    }
}
