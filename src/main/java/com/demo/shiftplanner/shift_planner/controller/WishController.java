package com.demo.shiftplanner.shift_planner.controller;

import com.demo.shiftplanner.shift_planner.dto.WishRequest;
import com.demo.shiftplanner.shift_planner.dto.WishResponseDTO;
import com.demo.shiftplanner.shift_planner.service.WishService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private static final Logger logger = LoggerFactory.getLogger(WishController.class);
    private final WishService wishService;
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<WishResponseDTO> addWish(@Valid @RequestBody WishRequest req) {
        WishResponseDTO dto = wishService.addWish(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDTO>> getWishes(
            @RequestParam String date,
            @RequestParam(required = false) String shift) {
        List<WishResponseDTO> list = wishService.getWishesByDateAndShift(date, shift);
        return ResponseEntity.ok(list);
    }
}
