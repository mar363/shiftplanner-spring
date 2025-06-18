package com.demo.shiftplanner.shift_planner;

import com.demo.shiftplanner.shift_planner.model.*;
import com.demo.shiftplanner.shift_planner.repository.AssignmentRepository;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import com.demo.shiftplanner.shift_planner.repository.WishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Parola hash-uită: " + admin.getPassword());
        }
    }
}
