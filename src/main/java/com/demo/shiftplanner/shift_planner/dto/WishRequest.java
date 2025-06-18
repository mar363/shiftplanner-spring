package com.demo.shiftplanner.shift_planner.dto;

import jakarta.validation.constraints.NotBlank;

public class WishRequest {

    private Long employeeId;

    @NotBlank
    private String date;

    @NotBlank
    private String shiftType;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }
}
