package com.demo.shiftplanner.shift_planner.dto;

import jakarta.validation.constraints.NotBlank;

public class PlanRequest {
    private Long adminId;

    @NotBlank
    private String date;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
