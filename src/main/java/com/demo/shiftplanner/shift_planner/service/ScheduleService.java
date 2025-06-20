package com.demo.shiftplanner.shift_planner.service;

import com.demo.shiftplanner.shift_planner.dto.AssignmentResponseDTO;
import com.demo.shiftplanner.shift_planner.exceptions.BusinessException;
import com.demo.shiftplanner.shift_planner.model.Assignment;
import com.demo.shiftplanner.shift_planner.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final AssignmentRepository assignmentRepository;

    public ScheduleService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<AssignmentResponseDTO> getScheduleForDate(String dateStr) {
        LocalDate date;
        try {
            date =  LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid date format! Insert date in format: YYYY-MM-DD");
        }
        List<Assignment> list = assignmentRepository.findByDate(date);
        return list.stream().map(a-> {
            AssignmentResponseDTO dto = new AssignmentResponseDTO(a.getId(), a.getEmployee().getId(), a.getDate().toString(), a.getShiftType().name());
            return dto;
        }).collect(Collectors.toList());
    }

}
