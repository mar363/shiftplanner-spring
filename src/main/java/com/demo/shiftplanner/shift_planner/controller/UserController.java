package com.demo.shiftplanner.shift_planner.controller;


import com.demo.shiftplanner.shift_planner.dto.CreateUserRequest;
import com.demo.shiftplanner.shift_planner.dto.UserResponseDTO;
import com.demo.shiftplanner.shift_planner.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody CreateUserRequest req) {
        return userService.createEmployee(req);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> list() {
        return userService.listEmployees();
    }
}
