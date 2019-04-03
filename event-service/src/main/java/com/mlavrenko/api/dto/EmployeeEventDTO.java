package com.mlavrenko.api.dto;

import com.mlavrenko.api.domain.enums.EmployeeStatus;

import java.util.Date;
import java.util.UUID;

public class EmployeeEventDTO {
    private UUID employeeId;
    private EmployeeStatus status;
    private Date eventCreated;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Date eventCreated) {
        this.eventCreated = eventCreated;
    }

    @Override
    public String toString() {
        return "EmployeeEventDTO{" +
                "employeeId=" + employeeId +
                ", status=" + status +
                ", eventCreated=" + eventCreated +
                '}';
    }
}
