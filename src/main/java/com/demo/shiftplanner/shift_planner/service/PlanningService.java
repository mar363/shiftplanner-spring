package com.demo.shiftplanner.shift_planner.service;

import com.demo.shiftplanner.shift_planner.dto.AssignmentResponseDTO;
import com.demo.shiftplanner.shift_planner.exceptions.BusinessException;
import com.demo.shiftplanner.shift_planner.model.*;
import com.demo.shiftplanner.shift_planner.repository.AssignmentRepository;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import com.demo.shiftplanner.shift_planner.repository.WishRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.http.FastHttpDateFormat.parseDate;

public class PlanningService {
    private final UserRepository userRepository;
    private final WishRepository wishRepository;
    private final AssignmentRepository assignmentRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PlanningService(UserRepository userRepository, WishRepository wishRepository, AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.wishRepository = wishRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public List<AssignmentResponseDTO> planForDate(String dateStr) {
        LocalDate date = parseDate(dateStr);
        logger.debug("Start planning");
        assignmentRepository.deleteByDate(date);
        logger.debug("Deleted old assignments for date {}", date);
        List<User> allEmployees = userRepository.findAllByRole(Role.EMPLOYEE);

        if (allEmployees.size() < 2) {
            throw new BusinessException("There are not employees for plan the schedule");
        }

        List<Long> allIds = allEmployees.stream().map(User::getId).collect(Collectors.toList());
        logger.debug("All employee IDs: {}", allIds);

        List<Wish> wishEarlyShift = wishRepository.findByDateAndShiftType(date, ShiftType.EARLY);
        List<Wish> wishLateShift = wishRepository.findByDateAndShiftType(date, ShiftType.LATE);

        List<Long> wishEarlyIds = wishEarlyShift.stream().map(w -> w.getEmployee().getId()).collect(Collectors.toList());

        List<Long> wishLateIds = wishLateShift.stream().map((w -> w.getEmployee().getId())).collect(Collectors.toList());

        Long chosenEarly = choseEarly(allIds, wishEarlyIds);
        Long chosenLate = choseLate(chosenEarly, allIds, wishLateIds);
        logger.debug("Chosen Early: {} , Chosen Late: {} ", chosenEarly, chosenLate);

        Assignment a1 = new Assignment(); //Early
        a1.setEmployee(userRepository.findById(chosenEarly).orElseThrow(() -> new BusinessException("Employee not found")));
        a1.setDate(date);
        a1.setShiftType(ShiftType.EARLY);
        assignmentRepository.save(a1);

        Assignment a2 = new Assignment(); //Late
        a2.setEmployee(userRepository.findById(chosenLate).orElseThrow(() -> new BusinessException("Employee not found")));
        a2.setDate(date);
        a2.setShiftType(ShiftType.LATE);
        assignmentRepository.save(a2);

        List<AssignmentResponseDTO> result = List.of(new AssignmentResponseDTO(a1.getId(), chosenEarly, date.toString(), ShiftType.EARLY.name()), new AssignmentResponseDTO(a2.getId(), chosenLate, date.toString(), ShiftType.LATE.name()));
        logger.debug("Planning result: {}", result);
        return result;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid format! YYYY-MM-DD");
        }
    }

    private Long choseEarly(List<Long> allIds, List<Long> wishEarlyIds) {
        if (!wishEarlyIds.isEmpty()) {
            return wishEarlyIds.get(0);
        }
        return allIds.get(0);
    }

    private Long choseLate(Long chosenEarly, List<Long> allIds, List<Long> wishLateIds) {
        List<Long> available = allIds.stream().filter(id -> !id.equals(chosenEarly)).collect(Collectors.toList());

        if (available.isEmpty()) {
            throw new BusinessException("There are not an available employee for LATE shift");
        }

        for (Long id : wishLateIds) {
            if (!id.equals(chosenEarly) && available.contains(id)) {
                return id;
            }
        }
        return available.get(0);
    }
}
