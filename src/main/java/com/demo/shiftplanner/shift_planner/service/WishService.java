package com.demo.shiftplanner.shift_planner.service;

import com.demo.shiftplanner.shift_planner.dto.WishRequest;
import com.demo.shiftplanner.shift_planner.dto.WishResponseDTO;
import com.demo.shiftplanner.shift_planner.exceptions.BusinessException;
import com.demo.shiftplanner.shift_planner.model.ShiftType;
import com.demo.shiftplanner.shift_planner.model.User;
import com.demo.shiftplanner.shift_planner.model.Wish;
import com.demo.shiftplanner.shift_planner.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final UserService userService;

    public WishService(WishRepository wishRepository, UserService userService) {
        this.wishRepository = wishRepository;
        this.userService = userService;
    }

    public WishResponseDTO addWish(WishRequest req) {
        Long empId = req.getEmployeeId();

        LocalDate date;

        try {
            date = LocalDate.parse(req.getDate());

        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid date format! Use YYYY-MM0-DD");
        }

        ShiftType shift;
        try {
            shift = ShiftType.valueOf(req.getShiftType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid shift. Chose between EARLY and LATE");
        }
        User emp = userService.findById(empId);

        if (wishRepository.findByEmployeeAndDate(emp, date).isPresent()) {
            throw new BusinessException("Employee'" + emp + " has already a shift prefference added");
        }

        Wish wish = new Wish();
        wish.setEmployee(emp);
        wish.setDate(date);
        wish.setShiftType(shift);
        Wish saved = wishRepository.save(wish);

        WishResponseDTO dto = new WishResponseDTO(
                saved.getId(),
                saved.getEmployee().getId(),
                saved.getDate().toString(),
                saved.getShiftType().name());
        return dto;
    }

    public List<WishResponseDTO> getWishesByDateAndShift(String dateStr, String shiftStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid format date. Use: YYYY-MM-DD");
        }
        List<Wish> wishes;

        if (shiftStr != null) {
            ShiftType shift = null;
            try {
                shift = ShiftType.valueOf(shiftStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("Invalid Shift. Chose between EARLY and LATE");
            }
            wishes = wishRepository.findByDateAndShiftType(date, shift);
        } else {
            wishes = wishRepository.findByDate(date);
        }

        return wishes.stream().map(w -> new WishResponseDTO(w.getId(),
                        w.getEmployee().getId(),
                        w.getDate().toString(),
                        w.getShiftType().name()))
                .collect(Collectors.toList());
    }
}
