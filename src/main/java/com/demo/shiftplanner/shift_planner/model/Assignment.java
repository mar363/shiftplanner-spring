package com.demo.shiftplanner.shift_planner.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name="assignments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id","date"}),
        @UniqueConstraint(columnNames = {"date","shift_type"})}
)
public class Assignment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="employee_id", nullable = false)
    private User employee;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftType shiftType;

    public Assignment() {
    }

    public Assignment(User employee, LocalDate date, ShiftType shiftType) {
        this.employee = employee;
        this.date = date;
        this.shiftType = shiftType;
    }

    public Long getId() {
        return id;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }


}
