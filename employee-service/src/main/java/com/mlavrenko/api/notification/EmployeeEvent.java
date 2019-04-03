package com.mlavrenko.api.notification;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class EmployeeEvent {
    private final UUID employeeId;
    private final EmployeeStatus status;
    private final Date eventCreated;

    public EmployeeEvent(UUID employeeId, EmployeeStatus status) {
        this.employeeId = employeeId;
        this.status = status;
        eventCreated = Date.from(Instant.now());
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    @Override
    public String toString() {
        return "EmployeeEvent{" +
                "employeeId=" + employeeId +
                ", status=" + status +
                ", eventCreated=" + eventCreated +
                '}';
    }
}
