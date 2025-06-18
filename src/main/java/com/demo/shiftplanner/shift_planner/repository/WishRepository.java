package com.demo.shiftplanner.shift_planner.repository;

import com.demo.shiftplanner.shift_planner.model.ShiftType;
import com.demo.shiftplanner.shift_planner.model.User;
import com.demo.shiftplanner.shift_planner.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByEmployeeAndDate(User user, LocalDate date);
    List<Wish> findByDateAndShiftType(LocalDate date, ShiftType shiftType);
}
