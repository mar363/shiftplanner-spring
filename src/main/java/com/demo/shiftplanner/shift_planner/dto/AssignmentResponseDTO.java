package com.demo.shiftplanner.shift_planner.dto;

public class AssignmentResponseDTO {
    private Long id;
    private Long employeeId;
    private String date;
    private String shift;

    public AssignmentResponseDTO(Long id, Long employeeId, String date, String shift) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.shift = shift;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getDate() {
        return date;
    }

    public String getShift() {
        return shift;
    }
}
