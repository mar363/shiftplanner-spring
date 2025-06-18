package com.demo.shiftplanner.shift_planner.repository;

import com.demo.shiftplanner.shift_planner.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByDate(LocalDate date);
}
