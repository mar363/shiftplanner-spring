package com.demo.shiftplanner.shift_planner.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name="wishes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "date"})
)
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftType shiftType;

    public Wish() {
    }

    public Wish(User employee, LocalDate date, ShiftType shiftType) {
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

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", employee=" + employee +
                ", date=" + date +
                ", shiftType=" + shiftType +
                '}';
    }
}
