package com.demo.shiftplanner.shift_planner.repository;

import com.demo.shiftplanner.shift_planner.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    void deleteByDate(@Param("date") LocalDate date);
    List<Assignment> findByDate(LocalDate date);
}
