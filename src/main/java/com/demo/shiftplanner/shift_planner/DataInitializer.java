package com.demo.shiftplanner.shift_planner;

import com.demo.shiftplanner.shift_planner.model.*;
import com.demo.shiftplanner.shift_planner.repository.AssignmentRepository;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import com.demo.shiftplanner.shift_planner.repository.WishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WishRepository wishRepository;
    private final AssignmentRepository assignmentRepository;
    public DataInitializer(UserRepository userRepository,
                           WishRepository wishRepository,
                           AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.wishRepository =wishRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void run(String ...args) throws Exception {

        List<User> users = userRepository.findAll();
        List<Wish> wishes = wishRepository.findAll();
        List<Assignment> assignments= assignmentRepository.findAll();
        users.forEach(System.out::println);
        wishes.forEach(System.out::println);
        assignments.forEach(System.out::println);
    }
}
