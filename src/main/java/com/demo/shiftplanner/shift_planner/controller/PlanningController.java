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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {
    private final PlanningService planningService;
    private final UserService userService;

    public PlanningController(PlanningService planningService, UserService userService) {
        this.planningService = planningService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AssignmentResponseDTO>> planForDate(@Valid @RequestBody PlanRequest req) {
        User admin = userService.findById(req.getAdminId());
        if (admin.getRole() != Role.ADMIN) {
            throw new BusinessException("Only Admin can plan a schedule");

        }
        List<AssignmentResponseDTO> result = planningService.planForDate(req.getDate());
        return ResponseEntity.ok(result);
    }
}
