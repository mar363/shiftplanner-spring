package com.demo.shiftplanner.shift_planner.controller;

import com.demo.shiftplanner.shift_planner.dto.AssignmentResponseDTO;
import com.demo.shiftplanner.shift_planner.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponseDTO>> viewSchedule(@RequestParam String date) {
        List<AssignmentResponseDTO> list = scheduleService.getScheduleForDate(date);
        return ResponseEntity.ok(list);
    }
}
