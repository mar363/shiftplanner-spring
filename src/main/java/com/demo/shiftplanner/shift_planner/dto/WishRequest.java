package com.demo.shiftplanner.shift_planner.dto;

import jakarta.validation.constraints.NotBlank;

public class WishRequest {

    private Long employeeId;

    @NotBlank
    private String date;

    @NotBlank
    private String shiftType;


    public WishRequest() {
    }

    public WishRequest(Long employeeId, String date, String shiftType) {
        this.employeeId = employeeId;
        this.date = date;
        this.shiftType = shiftType;
    }

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

    @Override
    public String toString() {
        return "WishRequest{" +
                "employeeId=" + employeeId +
                ", date='" + date + '\'' +
                ", shiftType='" + shiftType + '\'' +
                '}';
    }
}
