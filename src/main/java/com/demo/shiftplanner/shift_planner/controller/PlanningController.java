package com.demo.shiftplanner.shift_planner.controller;

import com.demo.shiftplanner.shift_planner.dto.AssignmentResponseDTO;
import com.demo.shiftplanner.shift_planner.dto.PlanRequest;
import com.demo.shiftplanner.shift_planner.exceptions.BusinessException;
import com.demo.shiftplanner.shift_planner.model.Role;
import com.demo.shiftplanner.shift_planner.model.User;
import com.demo.shiftplanner.shift_planner.service.PlanningService;
import com.demo.shiftplanner.shift_planner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planing")
public class PlanningController {
    private final PlanningService planningService;
    private final UserService userService;

    public PlanningController(PlanningService planningService, UserService userService) {
        this.planningService = planningService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<AssignmentResponseDTO>> planForDate(@Valid @RequestParam PlanRequest req) {
        User admin = userService.findById(req.getAdminId());
        if (admin.getRole() != Role.ADMIN) {
            throw new BusinessException("Only Admin can plan a schedule");

        }
        List<AssignmentResponseDTO> result = planningService.planForDate(req.getDate());
        return ResponseEntity.ok(result);
    }
}
