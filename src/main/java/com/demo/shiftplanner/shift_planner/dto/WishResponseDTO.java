package com.demo.shiftplanner.shift_planner.dto;

public class WishResponseDTO {

    private Long id;
    private Long employeeId;

    private String date;
    private String shift;

    public WishResponseDTO() {
    }

    public WishResponseDTO(Long id, Long employeeId, String date, String shift) {
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

    @Override
    public String toString() {
        return "WishResponseDTO{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", date='" + date + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}
